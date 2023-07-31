
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DevourAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PreyseizerDragon extends CardImpl {

    public PreyseizerDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Devour 2 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(2));

        // Whenever Preyseizer Dragon attacks, it deals damage to any target equal to the number of +1/+1 counters on Preyseizer Dragon.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(new CountersSourceCount(CounterType.P1P1)), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PreyseizerDragon(final PreyseizerDragon card) {
        super(card);
    }

    @Override
    public PreyseizerDragon copy() {
        return new PreyseizerDragon(this);
    }
}
