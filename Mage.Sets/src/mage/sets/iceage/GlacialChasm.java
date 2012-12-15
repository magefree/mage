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
package mage.sets.iceage;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class GlacialChasm extends CardImpl<GlacialChasm> {

    public GlacialChasm(UUID ownerId) {
        super(ownerId, 331, "Glacial Chasm", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ICE";

        // Cumulative upkeep-Pay 2 life.
        this.addAbility(new CumulativeUpkeepAbility(new PayLifeCost(2)));
        // When Glacial Chasm enters the battlefield, sacrifice a land.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeControllerEffect(new FilterLandPermanent(), 1, "")));
        // Creatures you control can't attack.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new CantAttackEffect()));
        // Prevent all damage that would be dealt to you.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new PreventAllDamageToControllerEffect()));
    }

    public GlacialChasm(final GlacialChasm card) {
        super(card);
    }

    @Override
    public GlacialChasm copy() {
        return new GlacialChasm(this);
    }
}
class SacrificeControllerEffect extends OneShotEffect<SacrificeControllerEffect>{

    private FilterPermanent filter;
    private DynamicValue count;

    public SacrificeControllerEffect ( FilterPermanent filter, DynamicValue count, String preText ) {
        super(Outcome.Sacrifice);
        this.filter = filter;
        this.count = count;
        staticText = "sacrifice a land";
    }

    public SacrificeControllerEffect ( FilterPermanent filter, int count, String preText ) {
        this(filter, new StaticValue(count), preText);
    }

    public SacrificeControllerEffect ( SacrificeControllerEffect effect ) {
        super(effect);
        this.filter = effect.filter;
        this.count = effect.count;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());

        if (player == null) {
            return false;
        }

        filter.add(new ControllerPredicate(Constants.TargetController.YOU));

        int amount = count.calculate(game, source);
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        amount = Math.min(amount, realCount);

        Target target = new TargetControlledPermanent(amount, amount, filter, false);
        target.setRequired(true);

        //A spell or ability could have removed the only legal target this player
        //had, if thats the case this ability should fizzle.
        if (amount > 0 && target.canChoose(source.getSourceId(), player.getId(), game)) {
            boolean abilityApplied = false;
            while (!target.isChosen() && target.canChoose(player.getId(), game)) {
                player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            }

            for ( int idx = 0; idx < target.getTargets().size(); idx++) {
                Permanent permanent = game.getPermanent((UUID)target.getTargets().get(idx));

                if ( permanent != null ) {
                    abilityApplied |= permanent.sacrifice(source.getSourceId(), game);
                }
            }

            return abilityApplied;
        }
        return false;
    }

    public void setAmount(DynamicValue amount) {
        this.count = amount;
    }

    @Override
    public SacrificeControllerEffect copy() {
        return new SacrificeControllerEffect(this);
    }

}


class CantAttackEffect extends ReplacementEffectImpl<CantAttackEffect> {

    public CantAttackEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Creatures you control can't attack";
    }

    public CantAttackEffect(final CantAttackEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackEffect copy() {
        return new CantAttackEffect(this);
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
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARE_ATTACKER && source.getControllerId().equals(event.getPlayerId())) {
            return true;
        }
        return false;
    }
}

class PreventAllDamageToControllerEffect extends PreventionEffectImpl<PreventAllDamageToControllerEffect> {


    public PreventAllDamageToControllerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all damage that would be dealt to you";
    }

    public PreventAllDamageToControllerEffect(final PreventAllDamageToControllerEffect effect) {
        super(effect);
    }

    @Override
    public PreventAllDamageToControllerEffect copy() {
        return new PreventAllDamageToControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())){
                return true;
            }

        }
        return false;
    }

}
