package com.fasttrack.cvgt.transfer;

public class GetMediasRequest {

    private String partialName;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    @Override
    public String toString() {
        return "GetMediasRequest{" +
                "partialName='" + partialName + '\'' +
                '}';
    }
}