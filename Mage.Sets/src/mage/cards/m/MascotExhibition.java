package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.LoreholdToken;
import mage.game.permanent.token.PrismariToken;
import mage.game.permanent.token.SilverquillToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MascotExhibition extends CardImpl {

    public MascotExhibition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}");

        this.subtype.add(SubType.LESSON);

        // Create a 2/1 white and black Inkling creature token with flying, a 3/2 red and white Spirit creature token, and a 4/4 blue and red Elemental creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SilverquillToken()));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new LoreholdToken())
                .setText(", a 3/2 red and white Spirit creature token"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PrismariToken())
                .setText(", and a 4/4 blue and red Elemental creature token"));
    }

    private MascotExhibition(final MascotExhibition card) {
        super(card);
    }

    @Override
    public MascotExhibition copy() {
        return new MascotExhibition(this);
    }
}
