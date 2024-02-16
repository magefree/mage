
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class ElectricEel extends CardImpl {

    public ElectricEel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Electric Eel enters the battlefield, it deals 1 damage to you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageControllerEffect(1, "it"), false));
        
        // {R}{R}: Electric Eel gets +2/+0 until end of turn and deals 1 damage to you.
        Effect effect = new DamageControllerEffect(1);
        effect.setText("and deals 1 damage to you");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}{R}"));
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ElectricEel(final ElectricEel card) {
        super(card);
    }

    @Override
    public ElectricEel copy() {
        return new ElectricEel(this);
    }
}
