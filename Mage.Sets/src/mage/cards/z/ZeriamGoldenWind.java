package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinRogueToken;
import mage.game.permanent.token.GriffinToken;

/**
 *
 * @author freaisdead
 */
public final class ZeriamGoldenWind extends CardImpl {
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Griffin you control");

    static {
        filter.add(SubType.GRIFFIN.getPredicate());
    }

    public ZeriamGoldenWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Griffin you control deals combat damage to a player, create a 2/2 white Griffin creature token with flying.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new GriffinToken()),
                filter, false, SetTargetPointer.NONE, true));
    }

    private ZeriamGoldenWind(final ZeriamGoldenWind card) {
        super(card);
    }

    @Override
    public ZeriamGoldenWind copy() {
        return new ZeriamGoldenWind(this);
    }
}
