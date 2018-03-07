package utilities.jiracukeplugin;

public enum JiraStatus {
    OPEN (1),
    IN_PROGRESS(3),
    STOP_PROGRESS(301);

    public final long value;

    JiraStatus(long value) {
        this.value = value;
    }
}
