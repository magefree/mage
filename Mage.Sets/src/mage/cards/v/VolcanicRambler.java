
package mage.cards.v;

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
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class VolcanicRambler extends CardImpl {

    public VolcanicRambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // {2}{R}: Volcanic Rambler deals 1 damage to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{2}{R}"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private VolcanicRambler(final VolcanicRambler card) {
        super(card);
    }

    @Override
    public VolcanicRambler copy() {
        return new VolcanicRambler(this);
    }
}
