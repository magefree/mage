
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class FlailingOgre extends CardImpl {

    public FlailingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}: Flailing Ogre gets +1/+1 until end of turn. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Duration.EndOfTurn) , new ManaCostsImpl<>("{1}"));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
        // {1}: Flailing Ogre gets -1/-1 until end of turn. Any player may activate this ability.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(-1, -1, Duration.EndOfTurn) , new ManaCostsImpl<>("{1}"));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private FlailingOgre(final FlailingOgre card) {
        super(card);
    }

    @Override
    public FlailingOgre copy() {
        return new FlailingOgre(this);
    }
}
