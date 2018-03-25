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
package mage.cards.m;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.RedirectDamageFromSourceToTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author L_J
 */
public class Martyrdom extends CardImpl {

    public Martyrdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");

        // Until end of turn, target creature you control gains "{0}: The next 1 damage that would be dealt to target creature or player this turn is dealt to this creature instead." Only you may activate this ability.
        this.getSpellAbility().addEffect(new MartyrdomGainAbilityTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    public Martyrdom(final Martyrdom card) {
        super(card);
    }

    @Override
    public Martyrdom copy() {
        return new Martyrdom(this);
    }
}

class MartyrdomGainAbilityTargetEffect extends ContinuousEffectImpl {

    public MartyrdomGainAbilityTargetEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Until end of turn, target creature you control gains \"{0}: The next 1 damage that would be dealt to target creature or player this turn is dealt to this creature instead.\" Only you may activate this ability";
    }
    
    public MartyrdomGainAbilityTargetEffect(final MartyrdomGainAbilityTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            ActivatedAbilityImpl ability = new MartyrdomActivatedAbility(source.getControllerId());
            ability.setMayActivate(TargetController.ANY);
            permanent.addAbility(ability, source.getSourceId(), game, false);
            return true;
        }
        return false;
    }

    @Override
    public MartyrdomGainAbilityTargetEffect copy() {
        return new MartyrdomGainAbilityTargetEffect(this);
    }
}

class MartyrdomActivatedAbility extends ActivatedAbilityImpl {
    
    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private UUID caster;
    
    public MartyrdomActivatedAbility(UUID caster) {
        super(Zone.BATTLEFIELD, new MartyrdomRedirectDamageTargetEffect(Duration.EndOfTurn, 1), new GenericManaCost(0));
        this.addTarget(new TargetCreatureOrPlayer());
        this.caster = caster;
    }

    private MartyrdomActivatedAbility(final MartyrdomActivatedAbility ability) {
        super(ability);
        this.caster = ability.caster;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        return super.getEffects(game, effectType);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (playerId.equals(caster)) {
            Permanent permanent = game.getBattlefield().getPermanent(this.getSourceId());
            if (permanent != null) {
                if (filter.match(permanent, permanent.getId(), permanent.getControllerId(), game)) {
                    return super.canActivate(playerId, game);
                }
            }
        }
        return false;
    }

    @Override
    public MartyrdomActivatedAbility copy() {
        return new MartyrdomActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return "{0}: The next 1 damage that would be dealt to target creature or player this turn is dealt to {this} instead.";
    }
}

class MartyrdomRedirectDamageTargetEffect extends RedirectionEffect {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public MartyrdomRedirectDamageTargetEffect(Duration duration, int amount) {
        super(duration, amount, true);
        staticText = "The next " + amount + " damage that would be dealt to target creature or player this turn is dealt to {this} instead";
    }

    public MartyrdomRedirectDamageTargetEffect(final MartyrdomRedirectDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public MartyrdomRedirectDamageTargetEffect copy() {
        return new MartyrdomRedirectDamageTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent != null) {
            if (filter.match(permanent, permanent.getId(), permanent.getControllerId(), game)) {
                if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
                    if (event.getTargetId() != null) {
                        TargetCreatureOrPlayer target = new TargetCreatureOrPlayer();
                        target.add(source.getSourceId(), game);
                        redirectTarget = target;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
