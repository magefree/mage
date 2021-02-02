
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GangrenousZombies extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter.add(SubType.SWAMP.getPredicate());
    }

    public GangrenousZombies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}, Sacrifice Gangrenous Zombies: Gangrenous Zombies deals 1 damage to each creature and each player. If you control a snow Swamp, Gangrenous Zombies deals 2 damage to each creature and each player instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalOneShotEffect(
                new DamageEverythingEffect(2),
                new DamageEverythingEffect(1),
                new PermanentsOnTheBattlefieldCondition(filter),
                "{this} deals 1 damage to each creature and each player. If you control a snow Swamp, {this} deals 2 damage to each creature and each player instead"),
                new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private GangrenousZombies(final GangrenousZombies card) {
        super(card);
    }

    @Override
    public GangrenousZombies copy() {
        return new GangrenousZombies(this);
    }
}
