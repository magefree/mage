
package mage.cards.f;

import java.util.*;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public final class Fatespinner extends CardImpl {

    public Fatespinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of each opponent's upkeep, that player chooses draw step, main phase, or combat phase. The player skips each instance of the chosen step or phase this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new FatespinnerChooseEffect(),
                TargetController.OPPONENT, false, true));
    }

    private Fatespinner(final Fatespinner card) {
        super(card);
    }

    @Override
    public Fatespinner copy() {
        return new Fatespinner(this);
    }
}

class FatespinnerChooseEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("Draw step");
        choices.add("Main phase");
        choices.add("Combat phase");
    }

    public FatespinnerChooseEffect() {
        super(Outcome.Detriment);
        staticText = "At the beginning of each opponent's upkeep, that player chooses draw step, main phase, or combat phase. The player skips each instance of the chosen step or phase this turn.";
    }

    public FatespinnerChooseEffect(final FatespinnerChooseEffect effect) {
        super(effect);
    }

    @Override
    public FatespinnerChooseEffect copy() {
        return new FatespinnerChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose phase or step to skip");
            choice.setChoices(choices);
            if (!player.choose(outcome, choice, game)) {
                return false;
            }
            String chosenPhase = choice.getChoice();
            game.informPlayers(player.getLogName() + " has chosen to skip " + chosenPhase.toLowerCase(Locale.ENGLISH) + '.');
            game.addEffect(new FatespinnerSkipEffect(chosenPhase), source);
            return true;
        }
        return false;
    }
}

class FatespinnerSkipEffect extends ReplacementEffectImpl {

    private final String phase;

    public FatespinnerSkipEffect(String phase) {
        super(Duration.EndOfTurn, Outcome.Detriment);
        this.phase = phase;
    }

    public FatespinnerSkipEffect(final FatespinnerSkipEffect effect) {
        super(effect);
        this.phase = effect.phase;
    }

    @Override
    public FatespinnerSkipEffect copy() {
        return new FatespinnerSkipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        EventType type = event.getType();
        return ((phase.equals("Draw step") && type == EventType.DRAW_STEP)
                || (phase.equals("Main phase") && (type == EventType.PRECOMBAT_MAIN_PHASE || type == EventType.POSTCOMBAT_MAIN_PHASE))
                || (phase.equals("Combat phase") && type == EventType.COMBAT_PHASE));
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        EventType type = event.getType();
        return (type == EventType.DRAW_STEP || type == EventType.PRECOMBAT_MAIN_PHASE
                || type == EventType.POSTCOMBAT_MAIN_PHASE || type == EventType.COMBAT_PHASE);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
