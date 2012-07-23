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
package mage.sets.magic2013;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.BlocksThisTurnMarkerAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class PredatoryRampage extends CardImpl<PredatoryRampage> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public PredatoryRampage(UUID ownerId) {
        super(ownerId, 180, "Predatory Rampage", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        this.expansionSetCode = "M13";

        this.color.setGreen(true);

        // Creatures you control get +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(3, 3, Duration.EndOfTurn));
        // Each creature your opponents control blocks this turn if able.
        this.getSpellAbility().addEffect(new AddContinuousEffectToGame(new PredatoryRampageEffect(Duration.EndOfTurn)));
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(BlocksThisTurnMarkerAbility.getInstance(), Duration.EndOfTurn, filter, ""));
    }

    public PredatoryRampage(final PredatoryRampage card) {
        super(card);
    }

    @Override
    public PredatoryRampage copy() {
        return new PredatoryRampage(this);
    }
}

class PredatoryRampageEffect extends RequirementEffect<PredatoryRampageEffect> {

    public PredatoryRampageEffect(Duration duration) {
        super(duration);
        this.staticText = "Each creature your opponents control blocks this turn if able";
    }

    public PredatoryRampageEffect(final PredatoryRampageEffect effect) {
        super(effect);
    }

    @Override
    public PredatoryRampageEffect copy() {
        return new PredatoryRampageEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && game.getOpponents(controller.getId()).contains(permanent.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public boolean mustBlockAny(Game game) {
        return true;
    }
}
