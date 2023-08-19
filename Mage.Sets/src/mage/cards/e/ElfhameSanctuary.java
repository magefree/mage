
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Markedagain
 */
public final class ElfhameSanctuary extends CardImpl {

    public ElfhameSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // At the beginning of your upkeep, you may search your library for a basic land card, reveal that card, and put it into your hand. If you do, you skip your draw step this turn and shuffle your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true
        ), TargetController.YOU, true);
        ability.addEffect(new SkipDrawStepThisTurn());

        this.addAbility(ability);
    }

    private ElfhameSanctuary(final ElfhameSanctuary card) {
        super(card);
    }

    @Override
    public ElfhameSanctuary copy() {
        return new ElfhameSanctuary(this);
    }
}

class SkipDrawStepThisTurn extends ReplacementEffectImpl {

    SkipDrawStepThisTurn() {
        super(Duration.UntilYourNextTurn, Outcome.Neutral);
        staticText = "Skip your draw step this turn";
    }

    private SkipDrawStepThisTurn(final SkipDrawStepThisTurn effect) {
        super(effect);
    }

    @Override
    public SkipDrawStepThisTurn copy() {
        return new SkipDrawStepThisTurn(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }
}
