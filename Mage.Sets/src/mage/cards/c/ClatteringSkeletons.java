package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClatteringSkeletons extends CardImpl {

    public ClatteringSkeletons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Clattering Skeletons dies, venture into the dungeon.
        this.addAbility(new DiesSourceTriggeredAbility(new VentureIntoTheDungeonEffect()));
    }

    private ClatteringSkeletons(final ClatteringSkeletons card) {
        super(card);
    }

    @Override
    public ClatteringSkeletons copy() {
        return new ClatteringSkeletons(this);
    }
}
