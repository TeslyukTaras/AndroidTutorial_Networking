package com.teslyuk.android.androidtutorial_volley_retrofit.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by taras on 15.02.16.
 */
public class Tag {
    @SerializedName("has_synonyms")
    @Expose
    private Boolean hasSynonyms;
    @SerializedName("is_moderator_only")
    @Expose
    private Boolean isModeratorOnly;
    @SerializedName("is_required")
    @Expose
    private Boolean isRequired;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * @return The hasSynonyms
     */
    public Boolean getHasSynonyms() {
        return hasSynonyms;
    }

    /**
     * @param hasSynonyms The has_synonyms
     */
    public void setHasSynonyms(Boolean hasSynonyms) {
        this.hasSynonyms = hasSynonyms;
    }

    /**
     * @return The isModeratorOnly
     */
    public Boolean getIsModeratorOnly() {
        return isModeratorOnly;
    }

    /**
     * @param isModeratorOnly The is_moderator_only
     */
    public void setIsModeratorOnly(Boolean isModeratorOnly) {
        this.isModeratorOnly = isModeratorOnly;
    }

    /**
     * @return The isRequired
     */
    public Boolean getIsRequired() {
        return isRequired;
    }

    /**
     * @param isRequired The is_required
     */
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * @return The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

}