
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToTargetMultiAmountEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author LoneFox

 */
public final class PollenRemedy extends CardImpl {

    private final UUID originalId;

    public PollenRemedy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledLandPermanent("a land"), true))));
        // Prevent the next 3 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose. If Pollen Remedy was kicked, prevent the next 6 damage this way instead.
        Effect effect = new ConditionalReplacementEffect(new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 6),
            KickedCondition.instance, new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 3));
        effect.setText("Prevent the next 3 damage that would be dealt this turn to any number of targets, divided as you choose. if this spell was kicked, prevent the next 6 damage this way instead.");
        this.getSpellAbility().addEffect(effect);
        originalId = this.getSpellAbility().getOriginalId();
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability.getOriginalId().equals(originalId)) {
             ability.addTarget(new TargetAnyTargetAmount(KickedCondition.instance.apply(game, ability) ? 6 : 3));
        }
    }

    public PollenRemedy(final PollenRemedy card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public PollenRemedy copy() {
        return new PollenRemedy(this);
    }
}
