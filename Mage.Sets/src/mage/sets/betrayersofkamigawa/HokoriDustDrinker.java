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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 * @author LevelX2
 */
public class HokoriDustDrinker extends CardImpl<HokoriDustDrinker> {

    public HokoriDustDrinker(UUID ownerId) {
        super(ownerId, 7, "Hokori, Dust Drinker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lands don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HokoriDustDrinkerReplacementEffect()));

        // At the beginning of each player's upkeep, that player untaps a land he or she controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new HokoriDustDrinkerUntapEffect(), Constants.TargetController.ANY, false));


    }

    public HokoriDustDrinker(final HokoriDustDrinker card) {
        super(card);
    }

    @Override
    public HokoriDustDrinker copy() {
        return new HokoriDustDrinker(this);
    }
}

class HokoriDustDrinkerReplacementEffect extends ReplacementEffectImpl<HokoriDustDrinkerReplacementEffect> {

    public HokoriDustDrinkerReplacementEffect() {
        super(Constants.Duration.OneUse, Constants.Outcome.Detriment);
    }

    public HokoriDustDrinkerReplacementEffect(final HokoriDustDrinkerReplacementEffect effect) {
        super(effect);
        staticText = "Lands don't untap during their controllers' untap steps";
    }

    @Override
    public HokoriDustDrinkerReplacementEffect copy() {
        return new HokoriDustDrinkerReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == Constants.PhaseStep.UNTAP &&
                event.getType() == GameEvent.EventType.UNTAP) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.getCardType().contains(CardType.LAND)) {
                return true;
            }
        }
        return false;
    }

}


class HokoriDustDrinkerUntapEffect extends OneShotEffect<HokoriDustDrinkerUntapEffect> {

    public HokoriDustDrinkerUntapEffect() {
        super(Outcome.Untap);
        this.staticText = "that player untaps a land he or she controls";
    }

    public HokoriDustDrinkerUntapEffect(final HokoriDustDrinkerUntapEffect effect) {
        super(effect);
    }

    @Override
    public HokoriDustDrinkerUntapEffect copy() {
        return new HokoriDustDrinkerUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        FilterLandPermanent filter = new FilterLandPermanent("land you control");
        filter.add(new ControllerIdPredicate(game.getActivePlayerId()));
        Target target = new TargetLandPermanent(filter);
        target.setRequired(true);
        if (player != null && player.chooseTarget(Outcome.Untap, target, source, game)) {
            for (UUID landId : target.getTargets()) {
                Permanent land = game.getPermanent(landId);
                if (land != null) {
                    land.untap(game);
                }
            }
            return true;
        }
        return false;
    }
}
