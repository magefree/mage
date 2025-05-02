package mage.cards.g;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GlimmerToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Glimmerburst extends CardImpl {

    public Glimmerburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Draw two cards. Create a 1/1 white Glimmer enchantment creature token.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GlimmerToken()));
    }

    private Glimmerburst(final Glimmerburst card) {
        super(card);
    }

    @Override
    public Glimmerburst copy() {
        return new Glimmerburst(this);
    }
}
