package mage.cards.b;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeholdTheMultiverse extends CardImpl {

    public BeholdTheMultiverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Scry 2, then draw two cards.
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy(", then"));

        // Foretell {1}{U}
        this.addAbility(new ForetellAbility(this, "{1}{U}"));
    }

    private BeholdTheMultiverse(final BeholdTheMultiverse card) {
        super(card);
    }

    @Override
    public BeholdTheMultiverse copy() {
        return new BeholdTheMultiverse(this);
    }
}
