package mage.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;

/**
 * Created by Dilnu on 9/4/16.
 */
public class ZoneChangeInfo {

    public boolean faceDown;
    public ZoneChangeEvent event;

    public ZoneChangeInfo(ZoneChangeEvent event) {
        this.event = event;
        this.faceDown = false;
    }

    public ZoneChangeInfo(ZoneChangeEvent event, boolean faceDown) {
        this(event);
        this.faceDown = faceDown;
    }

    public ZoneChangeInfo(ZoneChangeInfo info) {
        this.event = info.event;
        this.faceDown = info.faceDown;
    }

    public ZoneChangeInfo copy() {
        return new ZoneChangeInfo(this);
    }

    public static class Library extends ZoneChangeInfo {

        public boolean top;

        public Library(ZoneChangeEvent event, boolean top) {
            super(event);
            this.top = top;
        }

        public Library(ZoneChangeEvent event, boolean faceDown, boolean top) {
            super(event, faceDown);
            this.top = top;
        }

        public Library(final Library info) {
            super(info);
            this.top = info.top;
        }

        @Override
        public ZoneChangeInfo copy() {
            return new Library(this);
        }
    }

    public static class Exile extends ZoneChangeInfo {

        public UUID id;
        public String name;

        public Exile(ZoneChangeEvent event, UUID id, String name) {
            super(event);
            this.id = id;
            this.name = name;
        }

        public Exile(ZoneChangeEvent event, boolean faceDown, UUID id, String name) {
            super(event, faceDown);
            this.id = id;
            this.name = name;
        }

        public Exile(final Exile info) {
            super(info);
            this.id = info.id;
            this.name = info.name;
        }

        @Override
        public ZoneChangeInfo copy() {
            return new Exile(this);
        }
    }

    public static class Battlefield extends ZoneChangeInfo {

        public boolean tapped;

        public Battlefield(ZoneChangeEvent event, boolean tapped) {
            super(event);
            this.tapped = tapped;
        }

        public Battlefield(ZoneChangeEvent event, boolean faceDown, boolean tapped) {
            super(event, faceDown);
            this.tapped = tapped;
        }

        public Battlefield(final Battlefield info) {
            super(info);
            this.tapped = info.tapped;
        }

        @Override
        public ZoneChangeInfo copy() {
            return new Battlefield(this);
        }
    }

    public static class Stack extends ZoneChangeInfo {

        public Spell spell;

        public Stack(ZoneChangeEvent event, Spell spell) {
            super(event);
            this.spell = spell;
        }

        public Stack(ZoneChangeEvent event, boolean faceDown, Spell spell) {
            super(event, faceDown);
            this.spell = spell;
        }

        public Stack(final Stack info) {
            super(info);
            this.spell = info.spell;
        }

        @Override
        public ZoneChangeInfo copy() {
            return new Stack(this);
        }
    }

    // This class records state changes for grouped cards like Meld and Mutate.
    public static class Group extends ZoneChangeInfo {

        ZoneChangeInfo topInfo;
        List<ZoneChangeInfo> subInfo = new ArrayList<>();

        public Group(ZoneChangeInfo parent, Game game) {
            super(parent.event);
            topInfo = parent;
        }

        public void addGroupedCard(UUID groupedCard) {
            ZoneChangeEvent topEvent = new ZoneChangeEvent(groupedCard, event.getSourceId(),
                    event.getPlayerId(), event.getFromZone(), event.getToZone(), event.getAppliedEffects());
            ZoneChangeInfo newSubInfo = topInfo.copy();
            newSubInfo.event = topEvent;
            subInfo.add(newSubInfo);
        }
    }
}
