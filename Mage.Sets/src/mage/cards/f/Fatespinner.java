/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.f;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public class Fatespinner extends CardImpl {

    public Fatespinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of each opponent's upkeep, that player chooses draw step, main phase, or combat phase. The player skips each instance of the chosen step or phase this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new FatespinnerChooseEffect(),
                TargetController.OPPONENT, false, true));
    }

    public Fatespinner(final Fatespinner card) {
        super(card);
    }

    @Override
    public Fatespinner copy() {
        return new Fatespinner(this);
    }
}

class FatespinnerChooseEffect extends OneShotEffect {

    private static final HashSet<String> choices = new HashSet<>();

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
            while (!player.choose(outcome, choice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            String chosenPhase = choice.getChoice();
            game.informPlayers(player.getLogName() + " has chosen to skip " + chosenPhase.toLowerCase() + ".");
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
