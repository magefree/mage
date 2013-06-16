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
package mage.sets.shardsofalara;

import java.util.List;
import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.WatcherScope;
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
 * @author Plopman
 */
public class StoicAngel extends CardImpl<StoicAngel> {

    public StoicAngel(UUID ownerId) {
        super(ownerId, 199, "Stoic Angel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Angel");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Players can't untap more than one creature during their untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StoicAngelEffect()));
        this.addWatcher(new StoicAngelWatcher());
    }

    public StoicAngel(final StoicAngel card) {
        super(card);
    }

    @Override
    public StoicAngel copy() {
        return new StoicAngel(this);
    }
}

class StoicAngelWatcher extends WatcherImpl<StoicAngelWatcher> {

    public StoicAngelWatcher() {
        super("StoicAngelWatcher", WatcherScope.GAME);
    }

    public StoicAngelWatcher(final StoicAngelWatcher watcher) {
        super(watcher);
    }
    
    @Override
    public void watch(GameEvent event, Game game) {
         if(event.getType() == GameEvent.EventType.UNTAP_STEP_PRE){            
            game.getState().setValue("StoicAngelUntapCreature", null);
        }
    }

    @Override
    public StoicAngelWatcher copy() {
        return new StoicAngelWatcher(this);
    }
}

class StoicAngelEffect extends ReplacementEffectImpl<StoicAngelEffect> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature to untap");
    static{
        filter.add(new TappedPredicate());
    }
    public StoicAngelEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can't untap more than one creature during their untap steps";
    }

    public StoicAngelEffect(final StoicAngelEffect effect) {
        super(effect);
    }

    @Override
    public StoicAngelEffect copy() {
        return new StoicAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if(game.getState().getValue("StoicAngelUntapCreature") == null){
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, event.getPlayerId(), game);
            if(permanents.size() == 1){
                game.getState().setValue("StoicAngelUntapCreature", permanents.get(0).getId());
            }
            else if(permanents.size() > 1){
                Player player = game.getPlayer(event.getPlayerId());
                Target target = new TargetControlledCreaturePermanent(1, 1, filter, true, true);
                if(player != null && player.choose(Outcome.Untap, target, source.getId(), game)){
                    //TODO : This effect is bugged with other "don't untap effect".
                    game.getState().setValue("StoicAngelUntapCreature", target.getFirstTarget());
                }
            }
        }
        if(event.getTargetId().equals(game.getState().getValue("StoicAngelUntapCreature"))){
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
