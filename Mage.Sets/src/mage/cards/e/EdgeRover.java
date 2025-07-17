package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgeRover extends CardImpl {

    public EdgeRover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature dies, each player creates a Lander token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenAllEffect(new LanderToken(), TargetController.ANY)));
    }

    private EdgeRover(final EdgeRover card) {
        super(card);
    }

    @Override
    public EdgeRover copy() {
        return new EdgeRover(this);
    }
}
