package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathfindingAxejaw extends CardImpl {

    public PathfindingAxejaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Pathfinding Axejaw enters the battlefield, it explores.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExploreSourceEffect()));
    }

    private PathfindingAxejaw(final PathfindingAxejaw card) {
        super(card);
    }

    @Override
    public PathfindingAxejaw copy() {
        return new PathfindingAxejaw(this);
    }
}
