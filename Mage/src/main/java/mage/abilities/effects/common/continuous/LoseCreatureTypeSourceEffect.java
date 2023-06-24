package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class LoseCreatureTypeSourceEffect extends ContinuousEffectImpl {

    private final DynamicValue dynamicValue;
    private final int lessThan;

    /**
     * Permanent loses the creature type as long as the dynamic value is less
     * than the value of lessThan.
     *
     * @param dynamicValue
     * @param lessThan
     */
    public LoseCreatureTypeSourceEffect(DynamicValue dynamicValue, int lessThan) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.dynamicValue = dynamicValue;
        this.lessThan = lessThan;
        setText();
    }

    public LoseCreatureTypeSourceEffect(final LoseCreatureTypeSourceEffect effect) {
        super(effect);
        this.dynamicValue = effect.dynamicValue;
        this.lessThan = effect.lessThan;
    }

    @Override
    public LoseCreatureTypeSourceEffect copy() {
        return new LoseCreatureTypeSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game); //To change body of generated methods, choose Tools | Templates.
        if (duration.isOnlyValidIfNoZoneChange()) {
            // If source permanent is no longer onto battlefield discard the effect
            if (source.getSourcePermanentIfItStillExists(game) == null) {
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (dynamicValue.calculate(game, source, this) >= lessThan) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        permanent.removeCardType(game, CardType.CREATURE);
        if (!permanent.isTribal(game)) {
            permanent.removeAllCreatureTypes(game);
        }
        if (permanent.isAttacking() || permanent.getBlocking() > 0) {
            permanent.removeFromCombat(game);
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("As long as ");
        sb.append(dynamicValue.getMessage()).append(" is less than ");
        sb.append(CardUtil.numberToText(lessThan)).append(", {this} isn't a creature");
        staticText = sb.toString();
    }
}
