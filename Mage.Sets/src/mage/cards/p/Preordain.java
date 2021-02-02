package mage.cards.p;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Preordain extends CardImpl {

    public Preordain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Scry 2, then draw a card. (To scry 2, look at the top two cards of your library, then put any number of them on the bottom of your library and the rest on top in any order.)
        this.getSpellAbility().addEffect(
                new ScryEffect(2, false)
        );
        this.getSpellAbility().addEffect(
                new DrawCardSourceControllerEffect(1)
                        .setText(", then draw a card. <i>(To scry 2, "
                                + "look at the top two cards of your library, "
                                + "then put any number of them on the "
                                + "bottom of your library and the rest on "
                                + "top in any order.)</i>")
        );
    }

    private Preordain(final Preordain card) {
        super(card);
    }

    @Override
    public Preordain copy() {
        return new Preordain(this);
    }
}
