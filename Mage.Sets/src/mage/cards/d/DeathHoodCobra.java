
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class DeathHoodCobra extends CardImpl {

    public DeathHoodCobra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(ReachAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}")));
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}")));
    }

    private DeathHoodCobra(final DeathHoodCobra card) {
        super(card);
    }

    @Override
    public DeathHoodCobra copy() {
        return new DeathHoodCobra(this);
    }
}
