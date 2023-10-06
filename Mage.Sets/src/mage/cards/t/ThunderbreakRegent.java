package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ThunderbreakRegent extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Dragon you control");
    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public ThunderbreakRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control becomes the target of a spell or ability your opponent controls, Thunderbreak Regent deals 3 damage to that player.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new DamageTargetEffect(3).setText("{this} deals 3 damage to that player"),
                filter, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS, SetTargetPointer.PLAYER, false));
    }

    private ThunderbreakRegent(final ThunderbreakRegent card) {
        super(card);
    }

    @Override
    public ThunderbreakRegent copy() {
        return new ThunderbreakRegent(this);
    }
}
