package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FourKnocks extends CardImpl {

    public FourKnocks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Vanishing 4
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(4)));
        ability.setRuleVisible(false);
        this.addAbility(ability);
        this.addAbility(new VanishingUpkeepAbility(4));
        this.addAbility(new VanishingSacrificeAbility());

        // At the beginning of your precombat main phase, draw a card.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.YOU, false
        ));
    }

    private FourKnocks(final FourKnocks card) {
        super(card);
    }

    @Override
    public FourKnocks copy() {
        return new FourKnocks(this);
    }
}
