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
package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.LinkedHashSet;
import java.util.UUID;

/**
 *
 * @author Plopman
 */
public class CarpetOfFlowers extends CardImpl {

    public CarpetOfFlowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // At the beginning of each of your main phases, if you haven't added mana to your mana pool with this ability this turn, you may add up to X mana of any one color to your mana pool, where X is the number of Islands target opponent controls.
        this.addAbility(new CarpetOfFlowersTriggeredAbility());
    }

    public CarpetOfFlowers(final CarpetOfFlowers card) {
        super(card);
    }

    @Override
    public CarpetOfFlowers copy() {
        return new CarpetOfFlowers(this);
    }
}

class CarpetOfFlowersTriggeredAbility extends TriggeredAbilityImpl {

    CarpetOfFlowersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CarpetOfFlowersEffect(), true);
        this.addTarget(new TargetOpponent());
    }

    CarpetOfFlowersTriggeredAbility(final CarpetOfFlowersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CarpetOfFlowersTriggeredAbility copy() {
        return new CarpetOfFlowersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.PRECOMBAT_MAIN_PHASE_PRE
                || event.getType() == EventType.POSTCOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Boolean activatedThisTurn = (Boolean) game.getState().getValue(this.originalId.toString() + "addMana");
        if (activatedThisTurn == null) {
            return true;
        } else {
            return !activatedThisTurn;
        }
    }

    @Override
    public boolean resolve(Game game) {
        boolean value = super.resolve(game);
        if (value == true) {
            game.getState().setValue(this.originalId.toString() + "addMana", Boolean.TRUE);
        }
        return value;
    }

    @Override
    public void reset(Game game) {
        game.getState().setValue(this.originalId.toString() + "addMana", Boolean.FALSE);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("At the beginning of each of your main phases, if you haven't added mana to your mana pool with this ability this turn");
        return sb.append(super.getRule()).toString();
    }

}

class CarpetOfFlowersEffect extends ManaEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Island ");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    CarpetOfFlowersEffect() {
        super();
        staticText = "add up to X mana of any one color to your mana pool, where X is the number of Islands target opponent controls";
    }

    CarpetOfFlowersEffect(final CarpetOfFlowersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ChoiceColor choice = new ChoiceColor();
            while (!choice.isChosen()) {
                controller.choose(Outcome.Benefit, choice, game);
                if (!controller.canRespond()) {
                    return false;
                }
            }
            int countMax = game.getBattlefield().count(filter, source.getSourceId(), source.getTargets().getFirstTarget(), game);
            ChoiceImpl choiceCount = new ChoiceImpl(true);
            LinkedHashSet<String> set = new LinkedHashSet<>(countMax + 1);
            for (int i = 0; i <= countMax; i++) {
                set.add(Integer.toString(i));
            }
            choiceCount.setChoices(set);
            choiceCount.setMessage("Choose number of mana");
            controller.choose(Outcome.PutManaInPool, choiceCount, game);
            int count = 0;
            if (choiceCount.getChoice() != null) {
                count = Integer.parseInt(choiceCount.getChoice());
            }
            if (count > 0) {
                Mana mana = new Mana();
                switch (choice.getChoice()) {
                    case "Black":
                        mana.setBlack(count);
                        break;
                    case "Blue":
                        mana.setBlue(count);
                        break;
                    case "Red":
                        mana.setRed(count);
                        break;
                    case "Green":
                        mana.setGreen(count);
                        break;
                    case "White":
                        mana.setWhite(count);
                        break;
                    default:
                        break;
                }
                checkToFirePossibleEvents(mana, game, source);
                controller.getManaPool().addMana(mana, game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }

    @Override
    public CarpetOfFlowersEffect copy() {
        return new CarpetOfFlowersEffect(this);
    }
}
