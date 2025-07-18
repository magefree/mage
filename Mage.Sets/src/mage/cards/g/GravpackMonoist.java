package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GravpackMonoist extends CardImpl {

    public GravpackMonoist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature dies, create a tapped 2/2 colorless Robot artifact creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new RobotToken(), 1, true)));
    }

    private GravpackMonoist(final GravpackMonoist card) {
        super(card);
    }

    @Override
    public GravpackMonoist copy() {
        return new GravpackMonoist(this);
    }
}
