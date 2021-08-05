package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeteranDungeoneer extends CardImpl {

    public VeteranDungeoneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Veteran Dungeoneer enters the battlefield, venture into the dungeon.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VentureIntoTheDungeonEffect()));
    }

    private VeteranDungeoneer(final VeteranDungeoneer card) {
        super(card);
    }

    @Override
    public VeteranDungeoneer copy() {
        return new VeteranDungeoneer(this);
    }
}
