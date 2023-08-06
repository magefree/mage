
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author jonubuu
 */
public final class KeldonMarauders extends CardImpl {

    public KeldonMarauders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vanishing 2
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(2)));
        ability.setRuleVisible(false);
        this.addAbility(ability);
        this.addAbility(new VanishingUpkeepAbility(2));
        this.addAbility(new VanishingSacrificeAbility());

        // When Keldon Marauders enters the battlefield or leaves the battlefield, it deals 1 damage to target player.
        ability = new EntersBattlefieldOrLeavesSourceTriggeredAbility(new DamageTargetEffect(1, "it"), false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private KeldonMarauders(final KeldonMarauders card) {
        super(card);
    }

    @Override
    public KeldonMarauders copy() {
        return new KeldonMarauders(this);
    }
}
