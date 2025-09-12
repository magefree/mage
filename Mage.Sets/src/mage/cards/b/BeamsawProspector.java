package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeamsawProspector extends CardImpl {

    public BeamsawProspector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature dies, create a Lander token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new LanderToken())));
    }

    private BeamsawProspector(final BeamsawProspector card) {
        super(card);
    }

    @Override
    public BeamsawProspector copy() {
        return new BeamsawProspector(this);
    }
}
