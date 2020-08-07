package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FalconerAdept extends CardImpl {

    public FalconerAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Falconer Adept attacks, create a 1/1 white Bird creature token with flying that's tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(
                new CreateTokenEffect(new BirdToken(), 1, true, true), false
        ));
    }

    private FalconerAdept(final FalconerAdept card) {
        super(card);
    }

    @Override
    public FalconerAdept copy() {
        return new FalconerAdept(this);
    }
}
