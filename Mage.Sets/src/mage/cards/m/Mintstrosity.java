package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mintstrosity extends CardImpl {

    public Mintstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Mintstrosity dies, create a Food token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new FoodToken())));
    }

    private Mintstrosity(final Mintstrosity card) {
        super(card);
    }

    @Override
    public Mintstrosity copy() {
        return new Mintstrosity(this);
    }
}
