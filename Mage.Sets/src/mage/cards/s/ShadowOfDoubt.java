
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class ShadowOfDoubt extends CardImpl {

    public ShadowOfDoubt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U/B}{U/B}");


        // <i>({UB} can be paid with either {U} or {B}.)</i>
        // Players can't search libraries this turn.
        this.getSpellAbility().addEffect(new LibrariesCantBeSearchedEffect());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ShadowOfDoubt(final ShadowOfDoubt card) {
        super(card);
    }

    @Override
    public ShadowOfDoubt copy() {
        return new ShadowOfDoubt(this);
    }
}

class LibrariesCantBeSearchedEffect extends ContinuousRuleModifyingEffectImpl {

    public LibrariesCantBeSearchedEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, true, false);
        staticText = "Players can't search libraries this turn";
    }

    public LibrariesCantBeSearchedEffect(final LibrariesCantBeSearchedEffect effect) {
        super(effect);
    }

    @Override
    public LibrariesCantBeSearchedEffect copy() {
        return new LibrariesCantBeSearchedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == GameEvent.EventType.SEARCH_LIBRARY;
    }
}
