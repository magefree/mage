package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.JumblebonesToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OzoxTheClatteringKing extends CardImpl {

    public OzoxTheClatteringKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Ozox, the Clattering King can't block.
        this.addAbility(new CantBlockAbility());

        // When Ozox dies, create Jumblebones, a legendary 2/1 black Skeleton creature token with "This creature can't block" and "When this creature leaves the battlefield, return target card named Ozox, the Clattering King from your graveyard to your hand."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new JumblebonesToken())));
    }

    private OzoxTheClatteringKing(final OzoxTheClatteringKing card) {
        super(card);
    }

    @Override
    public OzoxTheClatteringKing copy() {
        return new OzoxTheClatteringKing(this);
    }
}
