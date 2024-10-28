package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SinisterMonolith extends CardImpl {

    public SinisterMonolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}");

        // At the beginning of combat on your turn, each opponent loses 1 life and you gain 1 life.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);

        // {T}, Pay 2 life, Sacrifice Sinister Monolith: Draw two cards. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new DrawCardSourceControllerEffect(2), new TapSourceCost()
        );
        ability.addCost(new PayLifeCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SinisterMonolith(final SinisterMonolith card) {
        super(card);
    }

    @Override
    public SinisterMonolith copy() {
        return new SinisterMonolith(this);
    }
}
