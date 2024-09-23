package com.nisum.oppenheimer.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @Column(name = "created", updatable = false)
    protected LocalDateTime created;

    @Column(name = "modified")
    protected LocalDateTime modified;

    @PrePersist
    public void onCreate() {
        created = LocalDateTime.now();
        modified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
}
