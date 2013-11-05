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
package mage.sets.commander2013;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import static mage.sets.commander2013.MysticBarrierChooseEffect.DIRECTION_CHOICE;

/**
 *
 * @author LevelX2
 */
public class MysticBarrier extends CardImpl<MysticBarrier> {

    public static final String ALLOW_ATTACKING_LEFT = "Allow attacking left";
    public static final String ALLOW_ATTACKING_RIGHT = "Allow attacking right";

    public MysticBarrier(UUID ownerId) {
        super(ownerId, 18, "Mystic Barrier", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");
        this.expansionSetCode = "C13";

        this.color.setWhite(true);

        // When Mystic Barrier enters the battlefield or at the beginning of your upkeep, choose left or right.
        this.addAbility(new MysticBarrierTriggeredAbility());

        // Each player may attack only the opponent seated nearest him or her in the last chosen direction and planeswalkers controlled by that player.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MysticBarrierReplacementEffect()));
    }

    public MysticBarrier(final MysticBarrier card) {
        super(card);
    }

    @Override
    public MysticBarrier copy() {
        return new MysticBarrier(this);
    }
}

class MysticBarrierTriggeredAbility extends TriggeredAbilityImpl<MysticBarrierTriggeredAbility> {

    public MysticBarrierTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MysticBarrierChooseEffect(), false);
    }

    public MysticBarrierTriggeredAbility(final MysticBarrierTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MysticBarrierTriggeredAbility copy() {
        return new MysticBarrierTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType().equals(EventType.ENTERS_THE_BATTLEFIELD) && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        if (event.getType().equals(EventType.UPKEEP_STEP_PRE) && event.getPlayerId().equals(this.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or at the beginning of your upkeep, " + super.getRule();
    }
}

class MysticBarrierChooseEffect extends OneShotEffect<MysticBarrierChooseEffect> {

    public static final String[] SET_VALUES = new String[] { MysticBarrier.ALLOW_ATTACKING_LEFT, MysticBarrier.ALLOW_ATTACKING_RIGHT };
    public static final Set<String> CHOICES = new HashSet<String>(Arrays.asList(SET_VALUES));
    final static Choice DIRECTION_CHOICE = new ChoiceImpl(true);
    static {
        DIRECTION_CHOICE.setChoices(CHOICES);
        DIRECTION_CHOICE.setMessage("Direction each player may only attack to");
        DIRECTION_CHOICE.isRequired();
    }

    public MysticBarrierChooseEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose left or right";
    }

    public MysticBarrierChooseEffect(final MysticBarrierChooseEffect effect) {
        super(effect);
    }

    @Override
    public MysticBarrierChooseEffect copy() {
        return new MysticBarrierChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            DIRECTION_CHOICE.clearChoice();
            while (!DIRECTION_CHOICE.isChosen() && controller.isInGame()) {
                controller.choose(outcome, DIRECTION_CHOICE, game);
            }
            if (!DIRECTION_CHOICE.getChoice().isEmpty()) {
                game.getState().setValue(new StringBuilder("attack_direction_").append(source.getSourceId()).toString(), DIRECTION_CHOICE.getChoice());
                return true;
            }

        }
        return false;
    }
}

class MysticBarrierReplacementEffect extends ReplacementEffectImpl<MysticBarrierReplacementEffect> {

    MysticBarrierReplacementEffect ( ) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each player may attack only the opponent seated nearest him or her in the last chosen direction and planeswalkers controlled by that player";
    }

    MysticBarrierReplacementEffect ( MysticBarrierReplacementEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER && game.getPlayers().size() > 2) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                if (controller.getInRange().contains(event.getPlayerId())) {
                    String allowedDirection = (String) game.getState().getValue(new StringBuilder("attack_direction_").append(source.getSourceId()).toString());
                    if (allowedDirection != null) {
                        Player defender = game.getPlayer(event.getTargetId());
                        if (defender == null) {
                            Permanent planeswalker = game.getPermanent(event.getTargetId());
                            if (planeswalker != null) {
                                defender = game.getPlayer(planeswalker.getControllerId());
                            }
                        }
                        if (defender != null) {
                            PlayerList playerList = game.getState().getPlayerList(event.getPlayerId());
                            if (allowedDirection.equals(MysticBarrier.ALLOW_ATTACKING_LEFT)) {
                                if (!playerList.getNext().equals(defender.getId())) {
                                    // the defender is not the player to the left
                                    return true;
                                }
                            }
                            if (allowedDirection.equals(MysticBarrier.ALLOW_ATTACKING_RIGHT)) {
                                if (!playerList.getPrevious().equals(defender.getId())) {
                                    // the defender is not the player to the right
                                    return true;
                                }
                            }
                        }
                        
                    }
                }
            }
        }
        return false;
    }

    @Override
    public MysticBarrierReplacementEffect copy() {
        return new MysticBarrierReplacementEffect(this);
    }

}
