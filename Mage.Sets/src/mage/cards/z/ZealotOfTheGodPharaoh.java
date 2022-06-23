
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author spjspj
 */
public final class ZealotOfTheGodPharaoh extends CardImpl {

    public ZealotOfTheGodPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR, SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {4}{R}: Zealot of the God-Pharaoh deals 2 damage to target opponent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{4}{R}"));
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private ZealotOfTheGodPharaoh(final ZealotOfTheGodPharaoh card) {
        super(card);
    }

    @Override
    public ZealotOfTheGodPharaoh copy() {
        return new ZealotOfTheGodPharaoh(this);
    }
}
