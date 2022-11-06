package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GruesomeRealization extends CardImpl {

    public GruesomeRealization(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Choose one --
        // * You draw two cards and you lose 2 life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText("you draw two cards"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));

        // * Creatures your opponents control get -1/-1 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        )));
    }

    private GruesomeRealization(final GruesomeRealization card) {
        super(card);
    }

    @Override
    public GruesomeRealization copy() {
        return new GruesomeRealization(this);
    }
}
