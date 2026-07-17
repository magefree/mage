package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FaerieBlueBlackToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PesteredWellguard extends CardImpl {

    public PesteredWellguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever this creature becomes tapped, create a 1/1 blue and black Faerie creature token with flying.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new CreateTokenEffect(new FaerieBlueBlackToken())));
    }

    private PesteredWellguard(final PesteredWellguard card) {
        super(card);
    }

    @Override
    public PesteredWellguard copy() {
        return new PesteredWellguard(this);
    }
}
