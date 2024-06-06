package mage.view;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.CardUtil;

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
    private final CardView original; // original card before transforms and modifications (null for opponents face down cards)
    private final boolean copy;
    private final String nameOwner; // only filled if != controller
    private final String nameController;
    private final boolean controlled;
    private final UUID attachedTo;
    private final boolean morphed;
    private final boolean disguised;
    private final boolean manifested;
    private final boolean cloaked;
    private final boolean attachedToPermanent;
    // If this card is attached to a permanent which is controlled by a player other than the one which controls this permanent
    private final boolean attachedControllerDiffers;

    public PermanentView(Permanent permanent, Card card, UUID createdForPlayerId, Game game) {
        super(permanent, game, CardUtil.canShowAsControlled(permanent, createdForPlayerId));
        this.controlled = permanent.getControllerId() != null && permanent.getControllerId().equals(createdForPlayerId);
        this.tapped = permanent.isTapped();
        this.flipped = permanent.isFlipped();
        this.phasedIn = permanent.isPhasedIn();
        this.summoningSickness = permanent.hasSummoningSickness();
        this.morphed = permanent.isMorphed();
        this.disguised = permanent.isDisguised();
        this.manifested = permanent.isManifested();
        this.cloaked = permanent.isCloaked();
        this.damage = permanent.getDamage();
        this.attachments = new ArrayList<>(permanent.getAttachments());
        this.attachedTo = permanent.getAttachedTo();

        // store original card, e.g. for sides switch in GUI
        if (isToken()) {
            original = new CardView(((PermanentToken) permanent).getToken().copy(), (Game) null);
            original.expansionSetCode = permanent.getExpansionSetCode(); // TODO: miss card number and other?
            expansionSetCode = permanent.getExpansionSetCode();
        } else {
            // face down card must be hidden from opponent, but shown on game end for all
            boolean showFaceDownInfo = controlled || (game != null && game.hasEnded());
            if (card != null && showFaceDownInfo) {
                original = new CardView(card.copy(), (Game) null);
            } else {
                original = null;
            }
        }
        //this.transformed = permanent.isTransformed();
        this.copy = permanent.isCopy();

        // for fipped, transformed or copied cards, switch the names
        if (original != null && !original.getName().equals(this.getName())) {
            // TODO: wtf, why copy check here?! Need research
            if (permanent.isCopy() && permanent.isFlipCard()) {
                this.alternateName = permanent.getFlipCardName();
            } else {
                this.alternateName = original.getName();
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

        String nameController = "";
        if (game != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                nameController = controller.getName();
            }
        }
        this.nameController = nameController;

        // determines if shown in it's own column
        boolean attachedToPermanent = false;
        boolean attachedControllerDiffers = false;
        if (game != null) {
            Permanent attachment = game.getPermanent(permanent.getAttachedTo());
            if (attachment != null) {
                attachedToPermanent = true;
                attachedControllerDiffers = !attachment.getControllerId().equals(permanent.getControllerId());
            }
        }
        this.attachedToPermanent = attachedToPermanent;
        this.attachedControllerDiffers = attachedControllerDiffers;
    }

    public PermanentView(PermanentView permanentView, Card card, UUID createdForPlayerId, Game game) {
        super(permanentView);
        this.controlled = permanentView.controlled;
        this.tapped = permanentView.isTapped();
        this.flipped = permanentView.isFlipped();
        this.phasedIn = permanentView.isPhasedIn();
        this.summoningSickness = permanentView.summoningSickness;
        this.damage = permanentView.damage;
        this.attachments = new ArrayList<>(permanentView.attachments);

        boolean showFaceDownInfo = controlled || (game != null && game.hasEnded());

        if (isToken()) {
            original = new CardView(permanentView.original);
            original.expansionSetCode = permanentView.original.getExpansionSetCode();
            expansionSetCode = permanentView.original.getExpansionSetCode();
        } else {
            if (card != null && showFaceDownInfo) {
                // face down card must be hidden from opponent, but shown on game end for all
                original = new CardView(card.copy(), (Game) null);
            } else {
                original = null;
            }
        }

        this.copy = permanentView.copy;
        this.nameOwner = permanentView.nameOwner;
        this.nameController = permanentView.nameController;
        this.attachedTo = permanentView.attachedTo;
        this.morphed = permanentView.morphed;
        this.disguised = permanentView.disguised;
        this.manifested = permanentView.manifested;
        this.cloaked = permanentView.cloaked;
        this.attachedToPermanent = permanentView.attachedToPermanent;
        this.attachedControllerDiffers = permanentView.attachedControllerDiffers;
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

    public String getNameController() {
        return nameController;
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

    public boolean isAttachedToDifferentlyControlledPermanent() {
        return attachedControllerDiffers;
    }

    public boolean isMorphed() {
        return morphed;
    }

    public boolean isDisguised() {
        return disguised;
    }

    public boolean isManifested() {
        return manifested;
    }

    public boolean isCloaked() {
        return cloaked;
    }
}
