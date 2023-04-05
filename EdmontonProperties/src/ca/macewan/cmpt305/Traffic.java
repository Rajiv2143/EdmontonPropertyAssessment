package ca.macewan.cmpt305;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Traffic {
    public StringProperty id,dateIssued,startDate,finishDate,status,closure,onStreet,
            fromStreet,toStreet,impact,duration,details,description,activityType,trafficDistrict,
            infrastructure,point;

    public Traffic(){
        this.id = new SimpleStringProperty();
        this.dateIssued = new SimpleStringProperty();
        this.startDate = new SimpleStringProperty();
        this.finishDate = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.closure = new SimpleStringProperty();
        this.onStreet = new SimpleStringProperty();
        this.fromStreet = new SimpleStringProperty();
        this.toStreet = new SimpleStringProperty();
        this.impact = new SimpleStringProperty();
        this.duration = new SimpleStringProperty();
        this.details = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.activityType = new SimpleStringProperty();
        this.trafficDistrict = new SimpleStringProperty();
        this.infrastructure = new SimpleStringProperty();
        this.point = new SimpleStringProperty();
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getDateIssued() {
        return dateIssued.get();
    }

    public StringProperty dateIssuedProperty() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued.set(dateIssued);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getFinishDate() {
        return finishDate.get();
    }

    public StringProperty finishDateProperty() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate.set(finishDate);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getClosure() {
        return closure.get();
    }

    public StringProperty closureProperty() {
        return closure;
    }

    public void setClosure(String closure) {
        this.closure.set(closure);
    }

    public String getOnStreet() {
        return onStreet.get();
    }

    public StringProperty onStreetProperty() {
        return onStreet;
    }

    public void setOnStreet(String onStreet) {
        this.onStreet.set(onStreet);
    }

    public String getFromStreet() {
        return fromStreet.get();
    }

    public StringProperty fromStreetProperty() {
        return fromStreet;
    }

    public void setFromStreet(String fromStreet) {
        this.fromStreet.set(fromStreet);
    }

    public String getToStreet() {
        return toStreet.get();
    }

    public StringProperty toStreetProperty() {
        return toStreet;
    }

    public void setToStreet(String toStreet) {
        this.toStreet.set(toStreet);
    }

    public String getImpact() {
        return impact.get();
    }

    public StringProperty impactProperty() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact.set(impact);
    }

    public String getDuration() {
        return duration.get();
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }

    public String getDetails() {
        return details.get();
    }

    public StringProperty detailsProperty() {
        return details;
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getActivityType() {
        return activityType.get();
    }

    public StringProperty activityTypeProperty() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType.set(activityType);
    }

    public String getTrafficDistrict() {
        return trafficDistrict.get();
    }

    public StringProperty trafficDistrictProperty() {
        return trafficDistrict;
    }

    public void setTrafficDistrict(String trafficDistrict) {
        this.trafficDistrict.set(trafficDistrict);
    }

    public String getInfrastructure() {
        return infrastructure.get();
    }

    public StringProperty infrastructureProperty() {
        return infrastructure;
    }

    public void setInfrastructure(String infrastructure) {
        this.infrastructure.set(infrastructure);
    }

    public String getPoint() {
        return point.get();
    }

    public StringProperty pointProperty() {
        return point;
    }

    public void setPoint(String point) {
        this.point.set(point);
    }
}