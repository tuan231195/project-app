package com.tuannguyen.projectapp.auth.entity;


import javax.annotation.Nonnull;
import javax.persistence.*;

@Entity
@Table(name = "access_level")
public class AccessLevel implements Comparable<AccessLevel> {
    public static final String NEW = "new";
    public static final String DISPLAY_TABLE_NAME = "Access Level";
    public static final String TABLE_NAME = "access_level";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private int rank;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessLevel that = (AccessLevel) o;

        if (id != that.id) return false;
        if (rank != that.rank) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + rank;
        return result;
    }


    @Override
    public int compareTo(@Nonnull AccessLevel o) {
        return o.rank - rank;
    }
}


