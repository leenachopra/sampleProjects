package utilities.jiracukeplugin;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/** sample code is :
 * https://community.atlassian.com/t5/Answers-Developer-Questions/Jira-Rest-Java-Client-Core-How-to-set-status-of-an-issue/qaq-p/502012
 */
public class JiraRestClientHlpr {

    private static final String JIRA_URL = "https://[the jira url]";
    private static final Logger LOGGER = LoggerFactory.getLogger(JiraRestClientHlpr.class);

    private Issue jiraIssue(JiraRestClient client, JiraInfo jiraInfo) {
        IssueRestClient issueClient = client.getIssueClient();
        List<IssueRestClient.Expandos> expand= new ArrayList<IssueRestClient.Expandos>();
        expand.add(IssueRestClient.Expandos.TRANSITIONS);
        Promise<Issue>  promisedParent = issueClient.getIssue(jiraInfo.getJiraId(),expand);
        Issue issue = promisedParent.claim();
        printAllTransitions(issueClient, issue);
        return issue;
    }

    private JiraRestClient getJiraRestClient() {
        JiraRestClientFactory restFactory = new AsynchronousJiraRestClientFactory();
        JiraRestClient client = null;
        URI jiraUri;

        try {
            jiraUri = new URI(JIRA_URL);
            client = restFactory.createWithBasicHttpAuthentication(
                    jiraUri, Auth.USER, Auth.PASSWORD);
            return client;
        } catch (URISyntaxException usx) {
            throw new RuntimeException(usx.getMessage());
        } finally{
            if (client != null)
                try {
                    client.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public void printAllTransitions(IssueRestClient issueClient, Issue issue) {
        Promise<Iterable<Transition>> ptransitions = issueClient.getTransitions(issue);
        Iterable<Transition> transitions = ptransitions.claim();

        for(Transition t: transitions){
            System.out.println(t.getName() + ":" + t.getId());
        }
        //System.out.println(issue.getStatus().getName());
    }

    public void updateJiraStatus(String jiraId, boolean runStatus ) {
        LOGGER.info("jiraId::" + jiraId + "  runStatus:: " + runStatus);
        JiraRestClient jiraClient = getJiraRestClient();
        JiraInfo jiraInfo = new JiraInfo();
        jiraInfo.setJiraId(jiraId);
        jiraInfo.setJiraUrl(JIRA_URL);
        Issue issue = jiraIssue(jiraClient, jiraInfo);
        printIssue(issue);

        // transition from OPEN to IN_PROGRESS
        if (issue.getStatus().getId() == JiraStatus.OPEN.value && runStatus) {
            TransitionInput tInput = new TransitionInput((int)JiraStatus.IN_PROGRESS.value);
            jiraClient.getIssueClient().transition(issue, tInput).claim();
        }
        LOGGER.info("Status is now updated to:: " + issue.getStatus().getName());
    }

    public void printIssue(Issue issue) {
        LOGGER.info("Description::" + issue.getDescription());
        LOGGER.info("Status::" + issue.getStatus());
        LOGGER.info("Key::" + issue.getKey());
        LOGGER.info("Priority::" + issue.getPriority());
        issue.getComments().forEach(c->LOGGER.info("Comment::" + c.toString()));
    }
}

