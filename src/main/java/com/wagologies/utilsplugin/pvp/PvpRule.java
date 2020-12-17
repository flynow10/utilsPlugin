package com.wagologies.utilsplugin.pvp;

public class PvpRule
{
    private String name;

    private PvpType type;

    private boolean isPvpOn;

    public PvpRule() { }

    public PvpRule(String name, PvpType type, boolean isPvpOn)
    {
        this.name = name;
        this.type = type;
        this.isPvpOn = isPvpOn;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setType(PvpType type)
    {
        this.type = type;
    }

    public void setPvpOn(boolean isPvpOn)
    {
        this.isPvpOn = isPvpOn;
    }

    public String getName()
    {
        return name;
    }

    public PvpType getType()
    {
        return type;
    }

    public boolean isPvpOn()
    {
        return isPvpOn;
    }

    public enum PvpType
    {
        PLAYER,
        WORLD;
        public PvpType next()
        {
            return values()[(this.ordinal()+1) % values().length];
        }
    }
}