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
package mage.cards.n;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class NakedSingularity extends CardImpl {

    public NakedSingularity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Cumulative upkeep {3}
        this.addAbility(new CumulativeUpkeepAbility(new GenericManaCost(3)));

        // If tapped for mana, Plains produce {R}, Islands produce {G}, Swamps produce {W}, Mountains produce {U}, and Forests produce {B} instead of any other type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new NakedSingularityEffect()));
    }

    public NakedSingularity(final NakedSingularity card) {
        super(card);
    }

    @Override
    public NakedSingularity copy() {
        return new NakedSingularity(this);
    }
}

class NakedSingularityEffect extends ReplacementEffectImpl {

    NakedSingularityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "If tapped for mana, Plains produce {R}, Islands produce {G}, Swamps produce {W}, Mountains produce {U}, and Forests produce {B} instead of any other type";
    }

    NakedSingularityEffect(final NakedSingularityEffect effect) {
        super(effect);
    }

    @Override
    public NakedSingularityEffect copy() {
        return new NakedSingularityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Pick a color to produce");
            if (permanent.hasSubtype("Plains", game)) {
                choice.getChoices().add("Red");
            }
            if (permanent.hasSubtype("Island", game)) {
                choice.getChoices().add("Green");
            }
            if (permanent.hasSubtype("Swamp", game)) {
                choice.getChoices().add("White");
            }
            if (permanent.hasSubtype("Mountain", game)) {
                choice.getChoices().add("Blue");
            }
            if (permanent.hasSubtype("Forest", game)) {
                choice.getChoices().add("Black");
            }
            String chosenColor;
            if (choice.getChoices().size() == 1) {
                chosenColor = choice.getChoices().iterator().next();
            }
            else {
                controller.choose(Outcome.PutManaInPool, choice, game);
                chosenColor = choice.getChoice();
            }
            ManaEvent manaEvent = (ManaEvent) event;
            Mana mana = manaEvent.getMana();
            int amount = mana.count();
            switch (chosenColor) {
                case "White":
                    mana.setToMana(Mana.WhiteMana(amount));
                    break;
                case "Blue":
                    mana.setToMana(Mana.BlueMana(amount));
                    break;
                case "Black":
                    mana.setToMana(Mana.BlackMana(amount));
                    break;
                case "Red":
                    mana.setToMana(Mana.RedMana(amount));
                    break;
                case "Green":
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
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && (permanent.hasSubtype("Plains", game)
                        || permanent.hasSubtype("Island", game)
                        || permanent.hasSubtype("Swamp", game)
                        || permanent.hasSubtype("Mountain", game)
                        || permanent.hasSubtype("Forest", game));
    }
}
