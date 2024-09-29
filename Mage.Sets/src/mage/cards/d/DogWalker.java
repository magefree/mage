package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WhiteDogToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DogWalker extends CardImpl {

    public DogWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Disguise {R/W}{R/W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{R/W}{R/W}")));

        // When Dog Walker is turned face up, create two tapped 1/1 white Dog creature tokens.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new CreateTokenEffect(new WhiteDogToken(), 2, true)));
    }

    private DogWalker(final DogWalker card) {
        super(card);
    }

    @Override
    public DogWalker copy() {
        return new DogWalker(this);
    }
}
