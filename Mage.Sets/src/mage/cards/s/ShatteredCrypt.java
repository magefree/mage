
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatteredCrypt extends CardImpl {

    public ShatteredCrypt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Return X target creature cards from your graveyard to your hand. You lose X life.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target creature cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().setTargetAdjuster(ShatteredCryptAdjuster.instance);
    }

    private ShatteredCrypt(final ShatteredCrypt card) {
        super(card);
    }

    @Override
    public ShatteredCrypt copy() {
        return new ShatteredCrypt(this);
    }
}

enum ShatteredCryptAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        Target target = new TargetCardInYourGraveyard(xValue, new FilterCreatureCard((xValue != 1 ? " creature cards" : "creature card") + " from your graveyard"));
        ability.addTarget(target);
    }
}