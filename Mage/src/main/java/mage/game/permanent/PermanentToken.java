package mage.game.permanent;

import java.util.UUID;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.Card;
import mage.cards.RoomCard;
import mage.constants.EmptyNames;
import mage.game.Game;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentToken extends PermanentImpl {

    // non-modifyable container with token characteristics
    // this PermanentToken resets to it on each game cycle
    // TODO: see PermanentCard.card for usage research and fixes
    protected Token token;

    public PermanentToken(Token token, UUID controllerId, Game game) {
        super(controllerId, controllerId, token.getName()); // random id
        this.token = token.copy();
        this.token.getAbilities().newOriginalId(); // neccessary if token has ability like DevourAbility()
        this.token.getAbilities().setSourceId(objectId);

        // if transformed on ETB
        if (this.token.isEntersTransformed()) {
            this.setTransformed(true);
            this.copyFromToken(this.token.getBackFace(), game, false);
        } else {
            this.copyFromToken(this.token, game, false); // needed to have at this time (e.g. for subtypes for entersTheBattlefield replacement effects)
        }

        // token's ZCC must be synced with original token to keep abilities settings
        // Example: kicker ability and kicked status
        if (game != null) { // game == null in GUI for card viewer's tokens
            this.setZoneChangeCounter(this.token.getZoneChangeCounter(game), game);
        }
    }

    protected PermanentToken(final PermanentToken permanent) {
        super(permanent);
        this.token = permanent.token.copy();
    }

    @Override
    public void reset(Game game) {
        if (this.isTransformed()) {
            copyFromToken(token.getBackFace(), game, true);
        } else {
            copyFromToken(token, game, true);
        }
        applyMutate(game);
        super.reset(game);
        // Because the P/T objects have there own base value for reset we have to take it from there instead of from the basic token object
        this.power.resetToBaseValue();
        this.toughness.resetToBaseValue();
    }

    @Override
    public int getManaValue() {
        if (this.isTransformed()) {
            // transformable permanents contains characteristics in token/token.getBackFace()

            // 712.8e
            // While a nonmodal double-faced permanent has its back face up, it has only the characteristics of its back face. 
            // However, its mana value is calculated using the mana cost of its front face. 
            // If a permanent is copying the back face of a nonmodal double-faced permanent (even if the object representing 
            // that copy is itself a double-faced permanent), the mana value of that permanent is 0. See rule 202.3b.
            return token.getBackFace().getManaValue();
        }
        if (faceDown) {
            return 0;
        }
        return super.getManaValue();
    }

    @Override
    public String getName() {
        if (name.isEmpty()) {
            return EmptyNames.FACE_DOWN_TOKEN.getObjectName();
        } else {
            return name;
        }
    }

    private void copyFromToken(Token token, Game game, boolean reset) {
        // modify all attributes permanently (without game usage)
        this.name = token.getName();
        this.abilities.clear();
        if (reset) {
            this.abilities.addAll(token.getAbilities());
        } else {
            // first time -> create ContinuousEffects only once
            // so sourceId must be null (keep triggered abilities forever?)
            for (Ability ability : token.getAbilities()) {
                //Don't add subabilities since the original token already has them in its abilities list
                this.addAbility(ability, null, game, true);
            }
        }
        this.abilities.setControllerId(this.controllerId);
        this.manaCost.clear();
        for (ManaCost cost : token.getManaCost()) {
            this.getManaCost().add(cost.copy());
        }
        this.cardType.clear();
        this.cardType.addAll(token.getCardType(game));
        this.color = token.getColor(game).copy();
        this.frameColor = token.getFrameColor(game);
        this.frameStyle = token.getFrameStyle();
        this.supertype.clear();
        this.supertype.addAll(token.getSuperType(game));
        this.subtype.copyFrom(token.getSubtype(game));
        this.startingLoyalty = token.getStartingLoyalty();
        this.startingDefense = token.getStartingDefense();
        // workaround for entersTheBattlefield replacement effects
        if (this.abilities.containsClass(ChangelingAbility.class)) {
            this.subtype.setIsAllCreatureTypes(true);
        }
        this.power = new MageInt(token.getPower().getModifiedBaseValue());
        this.toughness = new MageInt(token.getToughness().getModifiedBaseValue());
        CardUtil.copySetAndCardNumber(this, token);
        // no needs to add rooms characteristics here, because they already applied to source token
    }

    @Override
    public MageObject getBasicMageObject() {
        return token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public PermanentToken copy() {
        return new PermanentToken(this);
    }

    @Override
    public void updateZoneChangeCounter(Game game, ZoneChangeEvent event) {
        // token must change zcc on enters to battlefield (like cards do with stack),
        // so it can keep abilities settings synced with copied spell/card
        // example: kicker ability of copied creature spell
        super.updateZoneChangeCounter(game, event);
    }

    @Override
    public Card getMainCard() {
        // if you need original card of token then use getCopyFrom() instead of getMainCard()
        return super.getMainCard();
    }

    @Override
    public boolean isTransformable() {
        // 701.28c
        // If a spell or ability instructs a player to transform a permanent that
        // isn’t represented by a transforming token or a transforming double-faced card,
        // nothing happens.
        return token.getBackFace() != null;
    }

    @Override
    public MageObject getOtherFace() {
        return this.transformed ? token : this.token.getBackFace();
    }

    @Override
    protected void initOtherFace(Game game) {
        if (transformed) {
            copyFromToken(token.getBackFace(), game, false);
        } else {
            copyFromToken(token, game, false);
        }
    }

    @Override
    public boolean isCopy() {
        return token.isCopy();
    }

    @Override
    public MageObject getCopyFrom() {
        return token.getCopyFrom();
    }
}
