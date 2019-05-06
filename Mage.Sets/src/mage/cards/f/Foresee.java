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

        this.getSpellAbility().addEffect(new ScryEffect(4).setText("scry 4,"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText("then draw two cards"));
    }

    public Foresee(final Foresee card) {
        super(card);
    }

    @Override
    public Foresee copy() {
        return new Foresee(this);
    }
}
