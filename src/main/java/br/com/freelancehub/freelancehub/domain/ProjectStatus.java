package br.com.freelancehub.freelancehub.domain;

public enum ProjectStatus {
    IN_NEGOTIATION,
    IN_PROGRESS,
    COMPLETED,
    CANCELED;

    public boolean thisValidateTransaction(ProjectStatus newStatus) {
        return switch (this){
            case IN_NEGOTIATION -> newStatus == IN_PROGRESS || newStatus == CANCELED;
            case IN_PROGRESS -> newStatus == COMPLETED || newStatus == CANCELED;
            case COMPLETED, CANCELED-> false;
        };
    }
}