package mage.cards.g;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GodPharaohsStatue extends CardImpl {

    public GodPharaohsStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);

        // Spells your opponents cast cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostIncreasingAllEffect(2, new FilterCard("Spells"), TargetController.OPPONENT)));

        // At the beginning of your end step, each opponent loses 1 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new LoseLifeOpponentsEffect(1), TargetController.YOU, false
        ));
    }

    private GodPharaohsStatue(final GodPharaohsStatue card) {
        super(card);
    }

    @Override
    public GodPharaohsStatue copy() {
        return new GodPharaohsStatue(this);
    }
}
