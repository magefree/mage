package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FeatherToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EverquillPhoenix extends CardImpl {

    public EverquillPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mutate {3}{R}
        this.addAbility(new MutateAbility(this, "{3}{R}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature mutates, create a red artifact token named Feather with "{1}, Sacrifice Feather: Return target Phoenix card from your graveyard to the battlefield tapped."
        this.addAbility(new MutatesSourceTriggeredAbility(new CreateTokenEffect(new FeatherToken())));
    }

    private EverquillPhoenix(final EverquillPhoenix card) {
        super(card);
    }

    @Override
    public EverquillPhoenix copy() {
        return new EverquillPhoenix(this);
    }
}
