package mage.game;

import mage.cards.MeldCard;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

        public Library(Library info) {
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

        public Exile(Exile info) {
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

        public Battlefield (ZoneChangeEvent event, boolean tapped) {
            super(event);
            this.tapped = tapped;
        }

        public Battlefield (ZoneChangeEvent event, boolean faceDown, boolean tapped) {
            super(event, faceDown);
            this.tapped = tapped;
        }

        public Battlefield(Battlefield info) {
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

        public Stack(Stack info) {
            super(info);
            this.spell = info.spell;
        }

        @Override
        public ZoneChangeInfo copy() {
            return new Stack(this);
        }
    }

    public static class Unmelded extends ZoneChangeInfo {
        List<ZoneChangeInfo> subInfo = new ArrayList<>();
        public Unmelded(ZoneChangeInfo info, Game game) {
            super(info.event);
            MeldCard meld = game.getMeldCard(info.event.getTargetId());
            if (meld != null) {
                if (meld.hasTopHalf(game)) {
                    ZoneChangeEvent topEvent = new ZoneChangeEvent(meld.getTopHalfCard().getId(), event.getSourceId(),
                            event.getPlayerId(), event.getFromZone(), event.getToZone(), event.getAppliedEffects());
                    ZoneChangeInfo topInfo = info.copy();
                    topInfo.event = topEvent;
                    subInfo.add(topInfo);
                }
                if (meld.hasBottomHalf(game)) {
                    ZoneChangeEvent bottomEvent = new ZoneChangeEvent(meld.getBottomHalfCard().getId(), event.getSourceId(),
                            event.getPlayerId(), event.getFromZone(), event.getToZone(), event.getAppliedEffects());
                    ZoneChangeInfo bottomInfo = info.copy();
                    bottomInfo.event = bottomEvent;
                    subInfo.add(bottomInfo);
                }
            }
        }
    }
}
