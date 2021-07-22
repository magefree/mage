package mage.cards.c;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrushingDisappointment extends CardImpl {

    public CrushingDisappointment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Each player loses 2 life. You draw two cards.
        this.getSpellAbility().addEffect(new LoseLifeAllPlayersEffect(2));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).concatBy("You"));
    }

    private CrushingDisappointment(final CrushingDisappointment card) {
        super(card);
    }

    @Override
    public CrushingDisappointment copy() {
        return new CrushingDisappointment(this);
    }
}
