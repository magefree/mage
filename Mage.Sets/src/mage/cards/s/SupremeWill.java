package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class SupremeWill extends CardImpl {

    public SupremeWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose one &mdash; Counter target spell unless its controller pays {3}.;
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));

        // or Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        Mode mode = new Mode(new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.BOTTOM_ANY));
        this.getSpellAbility().addMode(mode);
    }

    private SupremeWill(final SupremeWill card) {
        super(card);
    }

    @Override
    public SupremeWill copy() {
        return new SupremeWill(this);
    }
}
