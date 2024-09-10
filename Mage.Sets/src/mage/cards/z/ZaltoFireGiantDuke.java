package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZaltoFireGiantDuke extends CardImpl {

    public ZaltoFireGiantDuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(7);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Zalto, Fire Giant Duke is dealt damage, venture into the dungeon.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new VentureIntoTheDungeonEffect(), false));
    }

    private ZaltoFireGiantDuke(final ZaltoFireGiantDuke card) {
        super(card);
    }

    @Override
    public ZaltoFireGiantDuke copy() {
        return new ZaltoFireGiantDuke(this);
    }
}
