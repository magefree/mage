
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.LastTimeCounterRemovedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.DeadlyGrubToken;

/**
 *
 * @author LoneFox
 */
public final class DeadlyGrub extends CardImpl {

    public DeadlyGrub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Vanishing 3
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(3)));
        ability.setRuleVisible(false);
        this.addAbility(ability);
        this.addAbility(new VanishingUpkeepAbility(3));
        this.addAbility(new VanishingSacrificeAbility());
        // When Deadly Grub dies, if it had no time counters on it, create a 6/1 green Insect creature token with shroud.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DiesTriggeredAbility(new CreateTokenEffect(new DeadlyGrubToken(), 1)),
                LastTimeCounterRemovedCondition.instance, "When {this} dies, if it had no time counters on it, create a 6/1 green Insect creature token with shroud."));
    }

    public DeadlyGrub(final DeadlyGrub card) {
        super(card);
    }

    @Override
    public DeadlyGrub copy() {
        return new DeadlyGrub(this);
    }
}
