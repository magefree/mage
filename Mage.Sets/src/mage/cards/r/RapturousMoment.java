package mage.cards.r;

import mage.Mana;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RapturousMoment extends CardImpl {

    public RapturousMoment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{R}");

        // Draw three cards, then discard two cards. Add {U}{U}{R}{R}{R}.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(3, 2));
        this.getSpellAbility().addEffect(new BasicManaEffect(new Mana(0, 2, 0, 3, 0, 0, 0, 0)));
    }

    private RapturousMoment(final RapturousMoment card) {
        super(card);
    }

    @Override
    public RapturousMoment copy() {
        return new RapturousMoment(this);
    }
}
