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
package mage.sets.limitedalpha;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author KholdFuzion

 */
public class Smoke extends CardImpl<Smoke> {

    public Smoke(UUID ownerId) {
        super(ownerId, 176, "Smoke", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}");
        this.expansionSetCode = "LEA";

        this.color.setRed(true);

        // Players can't untap more than one creature during their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SmokeEffect()));
        this.addWatcher(new SmokeWatcher());
    }

    public Smoke(final Smoke card) {
        super(card);
    }

    @Override
    public Smoke copy() {
        return new Smoke(this);
    }
}

class SmokeWatcher extends WatcherImpl<SmokeWatcher> {

    public SmokeWatcher() {
        super("SmokeWatcher", WatcherScope.GAME);
    }

    public SmokeWatcher(final SmokeWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if(event.getType() == GameEvent.EventType.UNTAP_STEP_PRE){
            game.getState().setValue("SmokeUntapCreature", null);
        }
    }

    @Override
    public SmokeWatcher copy() {
        return new SmokeWatcher(this);
    }
}

class SmokeEffect extends ReplacementEffectImpl<SmokeEffect> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature to untap");
    static{
        filter.add(new TappedPredicate());
    }
    public SmokeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't untap more than one creature during their untap steps";
    }

    public SmokeEffect(final SmokeEffect effect) {
        super(effect);
    }

    @Override
    public SmokeEffect copy() {
        return new SmokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if(game.getState().getValue("SmokeUntapCreature") == null){
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, event.getPlayerId(), game);
            if(permanents.size() == 1){
                game.getState().setValue("SmokeUntapCreature", permanents.get(0).getId());
            }
            else if(permanents.size() > 1){
                Player player = game.getPlayer(event.getPlayerId());
                Target target = new TargetControlledCreaturePermanent(1, 1, filter, true, true);
                if(player != null && player.choose(Outcome.Untap, target, source.getId(), game)){
                    //TODO : This effect is bugged with other "don't untap effect".Also affects Stoic Angel.
                    game.getState().setValue("SmokeUntapCreature", target.getFirstTarget());
                }
            }
        }
        if(event.getTargetId().equals(game.getState().getValue("SmokeUntapCreature"))){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == GameEvent.EventType.UNTAP){
            Player player = game.getPlayer(event.getPlayerId());
            Permanent permanent = game.getPermanent(event.getTargetId());
            if(player != null && game.getActivePlayerId().equals(event.getPlayerId())
                    && permanent != null && permanent.getCardType().contains(CardType.CREATURE)){
                return true;
            }
        }
        return false;
    }
}