package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlanarAlly extends CardImpl {

    public PlanarAlly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Planar Ally attacks, venture into the dungeon.
        this.addAbility(new AttacksTriggeredAbility(new VentureIntoTheDungeonEffect(), false));
    }

    private PlanarAlly(final PlanarAlly card) {
        super(card);
    }

    @Override
    public PlanarAlly copy() {
        return new PlanarAlly(this);
    }
}
