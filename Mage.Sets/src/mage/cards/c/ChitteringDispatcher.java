package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChitteringDispatcher extends CardImpl {

    public ChitteringDispatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Myriad
        this.addAbility(new MyriadAbility());

        // When Chittering Dispatcher leaves the battlefield, create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken()), false));
    }

    private ChitteringDispatcher(final ChitteringDispatcher card) {
        super(card);
    }

    @Override
    public ChitteringDispatcher copy() {
        return new ChitteringDispatcher(this);
    }
}
