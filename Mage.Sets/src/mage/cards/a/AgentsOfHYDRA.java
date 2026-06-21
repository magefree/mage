package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.game.permanent.token.VillainToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AgentsOfHYDRA extends CardImpl {

    public AgentsOfHYDRA(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature dies, create a 2/1 black Villain creature token with menace.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new VillainToken())));
    }

    private AgentsOfHYDRA(final AgentsOfHYDRA card) {
        super(card);
    }

    @Override
    public AgentsOfHYDRA copy() {
        return new AgentsOfHYDRA(this);
    }
}
