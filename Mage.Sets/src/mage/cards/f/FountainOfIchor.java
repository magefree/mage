package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FountainOfIchor extends CardImpl {

    public FountainOfIchor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {3}: Fountain of Ichor becomes a 3/3 Dinosaur artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new FountainOfIchorToken(), CardType.ARTIFACT, Duration.EndOfTurn
        ), new GenericManaCost(3)));
    }

    private FountainOfIchor(final FountainOfIchor card) {
        super(card);
    }

    @Override
    public FountainOfIchor copy() {
        return new FountainOfIchor(this);
    }
}

class FountainOfIchorToken extends TokenImpl {

    FountainOfIchorToken() {
        super("", "3/3 Dinosaur artifact creature");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.DINOSAUR);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private FountainOfIchorToken(final FountainOfIchorToken token) {
        super(token);
    }

    public FountainOfIchorToken copy() {
        return new FountainOfIchorToken(this);
    }
}
