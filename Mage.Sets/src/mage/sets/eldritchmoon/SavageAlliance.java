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
package mage.sets.eldritchmoon;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class SavageAlliance extends CardImpl {

    public SavageAlliance(UUID ownerId) {
        super(ownerId, 140, "Savage Alliance", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.expansionSetCode = "EMN";

        // Escalate {1}
        this.addAbility(new EscalateAbility(new GenericManaCost(1)));

        // Choose one or more &mdash;
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Creatures target player controls gain trample until end of turn.
        this.getSpellAbility().addEffect(new SavageAllianceGainTrampleEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Savage Alliance deals 2 damage to target creature.;
        Mode mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(2));
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // Savage Alliance deals 1 damage to each creature target opponent controls.
        mode = new Mode();
        mode.getEffects().add(new SavageAllianceDamageEffect());
        mode.getTargets().add(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    public SavageAlliance(final SavageAlliance card) {
        super(card);
    }

    @Override
    public SavageAlliance copy() {
        return new SavageAlliance(this);
    }
}

class SavageAllianceGainTrampleEffect extends OneShotEffect {

    public SavageAllianceGainTrampleEffect() {
        super(Outcome.AddAbility);
        staticText = "Creatures target player controls gain trample until end of turn";
    }

    public SavageAllianceGainTrampleEffect(final SavageAllianceGainTrampleEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public SavageAllianceGainTrampleEffect copy() {
        return new SavageAllianceGainTrampleEffect(this);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate((player.getId())));
            ContinuousEffect effect = new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class SavageAllianceDamageEffect extends OneShotEffect {

    public SavageAllianceDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to each creature target opponent controls";
    }

    public SavageAllianceDamageEffect(final SavageAllianceDamageEffect effect) {
        super(effect);
    }

    @Override
    public SavageAllianceDamageEffect copy() {
        return new SavageAllianceDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(player.getId()));
            List<Permanent> creatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
            for (Permanent creature : creatures) {
                creature.damage(1, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
