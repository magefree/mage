package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoonOfTheWishGiver extends CardImpl {

    public BoonOfTheWishGiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Draw four cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));

        // Cycling {1}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private BoonOfTheWishGiver(final BoonOfTheWishGiver card) {
        super(card);
    }

    @Override
    public BoonOfTheWishGiver copy() {
        return new BoonOfTheWishGiver(this);
    }
}
