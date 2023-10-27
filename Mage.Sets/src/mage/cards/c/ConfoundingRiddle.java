package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConfoundingRiddle extends CardImpl {

    public ConfoundingRiddle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose one --
        // * Look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                4, 1, PutCards.HAND, PutCards.GRAVEYARD
        ));

        // * Counter target spell unless its controller pays {4}.
        this.getSpellAbility().addMode(new Mode(new CounterUnlessPaysEffect(new GenericManaCost(4))).addTarget(new TargetSpell()));
    }

    private ConfoundingRiddle(final ConfoundingRiddle card) {
        super(card);
    }

    @Override
    public ConfoundingRiddle copy() {
        return new ConfoundingRiddle(this);
    }
}
