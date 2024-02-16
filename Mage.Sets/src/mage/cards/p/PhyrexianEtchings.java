
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author L_J
 */
public final class PhyrexianEtchings extends CardImpl {

    public PhyrexianEtchings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        // Cumulative upkeep-Pay {B}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{B}")));

        // At the beginning of your end step, draw a card for each age counter on Phyrexian Etchings.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.AGE)), false));

        // When Phyrexian Etchings is put into a graveyard from the battlefield, you lose 2 life for each age counter on it.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new LoseLifeSourceControllerEffect(new MultipliedValue(new CountersSourceCount(CounterType.AGE), 2))
                .setText("you lose 2 life for each age counter on it")
        ));
    }

    private PhyrexianEtchings(final PhyrexianEtchings card) {
        super(card);
    }

    @Override
    public PhyrexianEtchings copy() {
        return new PhyrexianEtchings(this);
    }
}
