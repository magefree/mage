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
package mage.sets.sorinvstibalt;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class TorrentOfSouls extends CardImpl {

    public TorrentOfSouls(UUID ownerId) {
        super(ownerId, 71, "Torrent of Souls", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{B/R}");
        this.expansionSetCode = "DDK";

        // Return up to one target creature card from your graveyard to the battlefield if {B} was spent to cast Torrent of Souls. Creatures target player controls get +2/+0 and gain haste until end of turn if {R} was spent to cast Torrent of Souls.
        Target targetCreature = new TargetCardInYourGraveyard(0, 1, new FilterCreatureCard("target creature card in your graveyard"));
        Target targetPlayer = new TargetPlayer();
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ReturnToBattlefieldUnderYourControlTargetEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.B), "Return up to one target creature card from your graveyard to the battlefield if {B} was spent to cast {this}"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new TorrentOfSoulsEffect(),
                new ManaWasSpentCondition(ColoredManaSymbol.R), " Creatures target player controls get +2/+0 and gain haste until end of turn if {R} was spent to cast {this}"));

        this.getSpellAbility().addTarget(targetCreature);
        this.getSpellAbility().addTarget(targetPlayer);

        this.addInfo("Info1", "<i>(Do both if {B}{R} was spent.)</i>");
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());

    }

    public TorrentOfSouls(final TorrentOfSouls card) {
        super(card);
    }

    @Override
    public TorrentOfSouls copy() {
        return new TorrentOfSouls(this);
    }
}

class TorrentOfSoulsEffect extends OneShotEffect {

    public TorrentOfSoulsEffect() {
        super(Outcome.BoostCreature);
    }

    public TorrentOfSoulsEffect(final TorrentOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public TorrentOfSoulsEffect copy() {
        return new TorrentOfSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetedPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (targetedPlayer != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(targetedPlayer.getId()));
            ContinuousEffect boostEffect = new BoostAllEffect(2, 0, Duration.EndOfTurn, filter, true);
            ContinuousEffect gainAbilityEffect = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter);
            game.addEffect(boostEffect, source);
            game.addEffect(gainAbilityEffect, source);
            return true;
        }
        return false;
    }
}
