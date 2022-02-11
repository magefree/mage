package mage.view;

import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentView extends CardView {

    private static final long serialVersionUID = 1L;

    private boolean tapped;
    private final boolean flipped;
    private final boolean phasedIn;
    private final boolean summoningSickness;
    private final int damage;
    private List<UUID> attachments;
    private final CardView original; // original card before transforms and modifications
    private final boolean copy;
    private final String nameOwner; // only filled if != controller
    private final boolean controlled;
    private final UUID attachedTo;
    private final boolean morphed;
    private final boolean manifested;
    private final boolean attachedToPermanent;

    public PermanentView(Permanent permanent, Card card, UUID createdForPlayerId, Game game) {
        super(permanent, game, permanent.getControllerId() != null && permanent.getControllerId().equals(createdForPlayerId));
        this.controlled = permanent.getControllerId() != null && permanent.getControllerId().equals(createdForPlayerId);
        this.rules = permanent.getRules(game);
        this.tapped = permanent.isTapped();
        this.flipped = permanent.isFlipped();
        this.phasedIn = permanent.isPhasedIn();
        this.summoningSickness = permanent.hasSummoningSickness();
        this.morphed = permanent.isMorphed();
        this.manifested = permanent.isManifested();
        this.damage = permanent.getDamage();
        if (!permanent.getAttachments().isEmpty()) {
            attachments = new ArrayList<>();
            attachments.addAll(permanent.getAttachments());
        }
        this.attachedTo = permanent.getAttachedTo();
        if (isToken()) {
            original = new CardView(((PermanentToken) permanent).getToken().copy(), (Game) null);
            original.expansionSetCode = permanent.getExpansionSetCode();
            tokenSetCode = original.getTokenSetCode();
            tokenDescriptor = original.getTokenDescriptor();
        } else {
            if (card != null) {
                // original may not be face down
                original = new CardView(card.copy(), (Game) null);
            } else {
                original = null;
            }
        }
        this.transformed = permanent.isTransformed();
        this.copy = permanent.isCopy();

        // for fipped, transformed or copied cards, switch the names
        if (original != null && !original.getName().equals(this.getName())) {
            if (permanent.isCopy() && permanent.isFlipCard()) {
                this.alternateName = permanent.getFlipCardName();
            } else {
                if (controlled // controller may always know
                        || (!morphed && !manifested)) { // others don't know for morph or transformed cards
                    this.alternateName = original.getName();
                }
            }
        }
        if (permanent.getOwnerId() != null && !permanent.getOwnerId().equals(permanent.getControllerId())) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            if (owner != null) {
                this.nameOwner = owner.getName();
            } else {
                this.nameOwner = "";
            }
        } else {
            this.nameOwner = "";
        }

        if (permanent.isFaceDown(game) && card != null) {
            if (controlled) {
                // must be a morphed or manifested card
                for (Ability permanentAbility : permanent.getAbilities(game)) {
                    if (permanentAbility.getWorksFaceDown()) {
                        this.rules.add(permanentAbility.getRule(true));
                    } else if (permanentAbility instanceof TurnFaceUpAbility && !permanentAbility.getRuleVisible()) {
                        this.rules.add(permanentAbility.getRule());
                    }
                }
                this.name = card.getName();
                this.displayName = card.getName();
                this.expansionSetCode = card.getExpansionSetCode();
                this.cardNumber = card.getCardNumber();
            } else {
                if (permanent.isManifested()) {
                    this.rules.add("A manifested creature card can be turned face up any time for it's mana cost."
                            + " A face-down card can also be turned face up for its morph cost.");
                } else if (permanent.isMorphed()) {
                    this.rules.add("If the controller has priority, they may turn this permanent face up."
                            + " This is a special action; it doesn't use the stack. To do this they pay the morph costs,"
                            + " then turns this permanent face up.");
                }
            }
        }
        // determines if shown in it's own column
        if (permanent.getAttachedTo() != null) {
            attachedToPermanent = game.getPermanent(permanent.getAttachedTo()) != null;
        } else {
            attachedToPermanent = false;
        }
    }

    public boolean isTapped() {
        return tapped;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public boolean isCopy() {
        return copy;
    }

    public boolean isPhasedIn() {
        return phasedIn;
    }

    public boolean hasSummoningSickness() {
        return summoningSickness;
    }

    public List<UUID> getAttachments() {
        return attachments;
    }

    public CardView getOriginal() {
        return original;
    }

    public void overrideTapped(boolean tapped) {
        this.tapped = tapped;
    }

    public String getNameOwner() {
        return nameOwner;
    }

    public boolean isControlled() {
        return controlled;
    }

    public UUID getAttachedTo() {
        return attachedTo;
    }

    public boolean isAttachedTo() {
        return attachedTo != null;
    }

    public boolean isAttachedToPermanent() {
        return attachedToPermanent;
    }

    public boolean isMorphed() {
        return morphed;
    }

    public boolean isManifested() {
        return manifested;
    }
}
