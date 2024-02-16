package mage.cards.o;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OtherworldlyGaze extends CardImpl {

    public OtherworldlyGaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Look at the top three cards of your library. Put any number of them into your graveyard and the rest back on top of your library in any order.
        this.getSpellAbility().addEffect(new SurveilEffect(3));

        // Flashback {1}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}")));
    }

    private OtherworldlyGaze(final OtherworldlyGaze card) {
        super(card);
    }

    @Override
    public OtherworldlyGaze copy() {
        return new OtherworldlyGaze(this);
    }
}
