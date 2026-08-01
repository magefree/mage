package mage.cards.c;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author muz
 */
public final class ConfusticateAndBebother extends CardImpl {

    public ConfusticateAndBebother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose one --
        // * Counter target spell unless its controller pays {4}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(4)));

        // * Draw two cards, then discard a card.
        this.getSpellAbility().addMode(new Mode(new DrawDiscardControllerEffect(2, 1)));
    }

    private ConfusticateAndBebother(final ConfusticateAndBebother card) {
        super(card);
    }

    @Override
    public ConfusticateAndBebother copy() {
        return new ConfusticateAndBebother(this);
    }
}
