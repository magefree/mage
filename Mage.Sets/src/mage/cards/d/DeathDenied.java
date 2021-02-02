
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeathDenied extends CardImpl {

    public DeathDenied(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        // Return X target creature cards from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target creature cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(DeathDeniedAdjuster.instance);
    }

    private DeathDenied(final DeathDenied card) {
        super(card);
    }

    @Override
    public DeathDenied copy() {
        return new DeathDenied(this);
    }
}

enum DeathDeniedAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        Target target = new TargetCardInYourGraveyard(xValue, new FilterCreatureCard(new StringBuilder(xValue).append(xValue != 1 ? " creature cards" : "creature card").append(" from your graveyard").toString()));
        ability.addTarget(target);
    }
}
