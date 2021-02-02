package mage.cards.f;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Foresee extends CardImpl {

    public Foresee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Scry 4, then draw two cards. (To scry 4, look at the top four cards of your library, then put any number of them on the bottom of your library and the rest on top in any order.)
        this.getSpellAbility().addEffect(new ScryEffect(4, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
    }

    private Foresee(final Foresee card) {
        super(card);
    }

    @Override
    public Foresee copy() {
        return new Foresee(this);
    }
}
