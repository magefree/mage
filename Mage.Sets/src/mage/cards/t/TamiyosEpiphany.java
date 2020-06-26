package mage.cards.t;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TamiyosEpiphany extends CardImpl {

    public TamiyosEpiphany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Scry 4, then draw two cards.
        this.getSpellAbility().addEffect(new ScryEffect(4, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));
    }

    private TamiyosEpiphany(final TamiyosEpiphany card) {
        super(card);
    }

    @Override
    public TamiyosEpiphany copy() {
        return new TamiyosEpiphany(this);
    }
}
