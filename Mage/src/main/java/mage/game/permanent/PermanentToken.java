
package mage.game.permanent;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PermanentToken extends PermanentImpl {

    protected Token token;

    public PermanentToken(Token token, UUID controllerId, String expansionSetCode, Game game) {
        super(controllerId, controllerId, token.getName());
        this.expansionSetCode = expansionSetCode;
        this.token = token.copy();
        this.token.getAbilities().newOriginalId(); // neccessary if token has ability like DevourAbility()
        this.token.getAbilities().setSourceId(objectId);
        this.power.modifyBaseValue(token.getPower().getBaseValueModified());
        this.toughness.modifyBaseValue(token.getToughness().getBaseValueModified());
        this.copyFromToken(this.token, game, false); // needed to have at this time (e.g. for subtypes for entersTheBattlefield replacement effects)
    }

    public PermanentToken(final PermanentToken permanent) {
        super(permanent);
        this.token = permanent.token.copy();
        this.expansionSetCode = permanent.expansionSetCode;
    }

    @Override
    public void reset(Game game) {
        copyFromToken(token, game, true);
        super.reset(game);
        // Because the P/T objects have there own base value for reset we have to take it from there instead of from the basic token object
        this.power.resetToBaseValue();
        this.toughness.resetToBaseValue();
    }

    private void copyFromToken(Token token, Game game, boolean reset) {
        this.name = token.getName();
        this.abilities.clear();
        if (reset) {
            this.abilities.addAll(token.getAbilities());
        } else {
            // first time -> create ContinuousEffects only once
            for (Ability ability : token.getAbilities()) {
                this.addAbility(ability, game);
            }
        }
        this.abilities.setControllerId(this.controllerId);
        this.manaCost.clear();
        for (ManaCost cost : token.getManaCost()) {
            this.getManaCost().add(cost.copy());
        }
        this.cardType.clear();
        this.cardType.addAll(token.getCardType());
        this.color = token.getColor(game).copy();
        this.frameColor = token.getFrameColor(game);
        this.frameStyle = token.getFrameStyle();
        this.supertype.clear();
        this.supertype.addAll(token.getSuperType());
        this.subtype.clear();
        this.subtype.addAll(token.getSubtype(game));
        this.isAllCreatureTypes = token.isAllCreatureTypes();
        this.tokenDescriptor = token.getTokenDescriptor();
    }

    @Override
    public MageObject getBasicMageObject(Game game) {
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
    public void adjustTargets(Ability ability, Game game) {
        if (getToken().getCopySourceCard() != null) {
            getToken().getCopySourceCard().adjustTargets(ability, game);
        } else {
            super.adjustTargets(ability, game);
        }
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (getToken().getCopySourceCard() != null) {
            getToken().getCopySourceCard().adjustCosts(ability, game);
        } else {
            super.adjustCosts(ability, game);
        }
    }

}
