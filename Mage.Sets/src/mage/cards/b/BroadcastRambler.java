package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BroadcastRambler extends CardImpl {

    public BroadcastRambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When this Vehicle enters, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private BroadcastRambler(final BroadcastRambler card) {
        super(card);
    }

    @Override
    public BroadcastRambler copy() {
        return new BroadcastRambler(this);
    }
}
