package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DragonToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonTrainer extends CardImpl {

    public DragonTrainer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, create a 4/4 red Dragon creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DragonToken())));
    }

    private DragonTrainer(final DragonTrainer card) {
        super(card);
    }

    @Override
    public DragonTrainer copy() {
        return new DragonTrainer(this);
    }
}
