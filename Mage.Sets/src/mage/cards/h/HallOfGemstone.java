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
package mage.cards.h;

import java.util.UUID;
import mage.MageObject;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUntapTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class HallOfGemstone extends CardImpl {

    public HallOfGemstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        this.supertype.add("World");

        // At the beginning of each player's upkeep, that player chooses a color. Until end of turn, lands tapped for mana produce mana of the chosen color instead of any other color.
        this.addAbility(new BeginningOfUntapTriggeredAbility(new HallOfGemstoneEffect(), TargetController.ANY, false));

    }

    public HallOfGemstone(final HallOfGemstone card) {
        super(card);
    }

    @Override
    public HallOfGemstone copy() {
        return new HallOfGemstone(this);
    }
}

class HallOfGemstoneEffect extends ReplacementEffectImpl {

    HallOfGemstoneEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
        staticText = "that player chooses a color. Until end of turn, lands tapped for mana produce mana of the chosen color instead of any other color";
    }

    HallOfGemstoneEffect(final HallOfGemstoneEffect effect) {
        super(effect);
    }

    @Override
    public HallOfGemstoneEffect copy() {
        return new HallOfGemstoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject mageObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && mageObject != null) {
            ChoiceColor choice = new ChoiceColor();
            while (!choice.isChosen()) {
                player.choose(outcome, choice, game);
                if (!player.canRespond()) {
                    return;
                }
            }
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getLogName() + ": " + player.getLogName() + " has chosen " + choice.getChoice());
            }
            game.getState().setValue(mageObject.getId() + "_color", choice.getColor());
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo("chosen color", CardUtil.addToolTipMarkTags("Chosen color: " + choice.getChoice()), game);
            }
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ObjectColor colorChosen = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (colorChosen != null) {
            ManaEvent manaEvent = (ManaEvent) event;
            Mana mana = manaEvent.getMana();
            int amount = mana.count();
            switch (colorChosen.getColoredManaSymbol()) {
                case W:
                    mana.setToMana(Mana.WhiteMana(amount));
                    break;
                case U:
                    mana.setToMana(Mana.BlueMana(amount));
                    break;
                case B:
                    mana.setToMana(Mana.BlackMana(amount));
                    break;
                case R:
                    mana.setToMana(Mana.RedMana(amount));
                    break;
                case G:
                    mana.setToMana(Mana.GreenMana(amount));
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null && permanent.isLand();
    }
}
