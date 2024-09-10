package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CaseAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.SolvedSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.common.CaseSolvedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class CaseOfTheShiftingVisage extends CardImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell("a nonlegendary creature spell");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public CaseOfTheShiftingVisage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");
        this.subtype.add(SubType.CASE);

        // At the beginning of your upkeep, surveil 1.
        Ability initialAbility = new BeginningOfUpkeepTriggeredAbility(new SurveilEffect(1, false), TargetController.YOU, false);
        // To solve — There are fifteen or more cards in your graveyard.
        Condition toSolveCondition = new CardsInControllerGraveyardCondition(15);
        // Solved — Whenever you cast a nonlegendary creature spell, copy that spell.
        Ability solvedAbility = new ConditionalTriggeredAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetStackObjectEffect(true).setText("copy that spell. <i>(The copy becomes a token.)</i>"), filter, false, SetTargetPointer.SPELL
        ), SolvedSourceCondition.SOLVED, null);

        this.addAbility(new CaseAbility(initialAbility, toSolveCondition, solvedAbility)
                .addHint(new CaseOfTheShiftingVisageHint(toSolveCondition)));
    }

    private CaseOfTheShiftingVisage(final CaseOfTheShiftingVisage card) {
        super(card);
    }

    @Override
    public CaseOfTheShiftingVisage copy() {
        return new CaseOfTheShiftingVisage(this);
    }
}

class CaseOfTheShiftingVisageHint extends CaseSolvedHint {

    CaseOfTheShiftingVisageHint(Condition condition) {
        super(condition);
    }

    private CaseOfTheShiftingVisageHint(final CaseOfTheShiftingVisageHint hint) {
        super(hint);
    }

    @Override
    public CaseOfTheShiftingVisageHint copy() {
        return new CaseOfTheShiftingVisageHint(this);
    }

    @Override
    public String getConditionText(Game game, Ability source) {
        UUID playerId = source.getControllerId();
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return "";
        }
        int value = player.getGraveyard().count(StaticFilters.FILTER_CARD, playerId, source, game);
        return "Cards in graveyard: " + value + " (need 15).";
    }
}
