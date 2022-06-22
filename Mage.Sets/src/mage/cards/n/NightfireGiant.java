
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class NightfireGiant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Mountain");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());

    }

    public NightfireGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Nightfire Giant gets +1/+1 as long as you control a Mountain.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 1, 1)));

        // {4}{R}: Nightfire Giant deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{4}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private NightfireGiant(final NightfireGiant card) {
        super(card);
    }

    @Override
    public NightfireGiant copy() {
        return new NightfireGiant(this);
    }
}
