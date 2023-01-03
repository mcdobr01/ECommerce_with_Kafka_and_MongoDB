package com.dbm.main.mongo.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("kafkamessages")
public class KafkaLogMessage {

	@Id
	private String id;

	private final String topic = "logMessage";
	private String severity;
	private String source;
	private String messageId;
	private Date logDate;

	public KafkaLogMessage(String severity, String source, String messageId, Date logDate) {
	    super();
	    this.severity = severity;
	    this.source = source;
	    this.messageId = messageId;
	    
	    if (logDate != null) {
	    	this.logDate = logDate;
	    }else {
	    	this.logDate = Calendar.getInstance().getTime();
	    }
	}
	
	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public Date getMessageDate() {
		return logDate;
	}

	public void setMessageDate(Date messageDate) {
		this.logDate = messageDate;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	public String toString() {
		return "{\n\tid:\"" + id
				+ "\",\n\ttopic:\"" + topic
				+ "\",\n\tseverity:\"" + severity
				+ "\",\n\tsource:\"" + source
				+ "\",\n\tmessageDate:\"" + logDate.toString()
				+ "\",\n\tmessageId:" + messageId
				+ "\n}";
	}
	
	@Override
	public boolean equals(Object o) {
		KafkaLogMessage om = (KafkaLogMessage)o;
		return this.id.equals(om.id)
				&& this.topic.equals(om.topic)
				&& this.severity.equals(om.severity)
				&& this.source.equals(om.source)
				&& this.messageId.equals(om.messageId)
				&& this.logDate.equals(om.logDate);
	}
}
