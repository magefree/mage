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
package mage.sets.gatecrash;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.WinGameEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class HellkiteTyrant extends CardImpl<HellkiteTyrant> {

    public HellkiteTyrant(UUID ownerId) {
        super(ownerId, 94, "Hellkite Tyrant", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Hellkite Tyrant deals combat damage to a player, gain control of all artifacts that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HellkiteTyrantEffect(),false, true));

        // At the beginning of your upkeep, if you control twenty or more artifacts, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new WinGameEffect(), Constants.TargetController.YOU, false);
        this.addAbility(new ConditionalTriggeredAbility(
                ability,
                new ControlsPermanentCondition(new FilterArtifactPermanent(), ControlsPermanentCondition.CountType.MORE_THAN,19),
                "At the beginning of your upkeep, if you control twenty or more artifacts, you win the game."));

    }

    public HellkiteTyrant(final HellkiteTyrant card) {
        super(card);
    }

    @Override
    public HellkiteTyrant copy() {
        return new HellkiteTyrant(this);
    }
}

class HellkiteTyrantEffect extends OneShotEffect<HellkiteTyrantEffect> {

    public HellkiteTyrantEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of all artifacts that player controls";
    }

    public HellkiteTyrantEffect(final HellkiteTyrantEffect effect) {
        super(effect);
    }

    @Override
    public HellkiteTyrantEffect copy() {
        return new HellkiteTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        FilterPermanent filter = new FilterArtifactPermanent();
        filter.add(new ControllerIdPredicate(player.getId()));

        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId() , game);
        for (Permanent permanent : permanents) {
            ContinuousEffect effect = new HellkiteTyrantControlEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(permanent.getId()));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class HellkiteTyrantControlEffect extends ContinuousEffectImpl<HellkiteTyrantControlEffect> {

    private UUID controllerId;

    public HellkiteTyrantControlEffect(UUID controllerId) {
        super(Duration.WhileOnBattlefield, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public HellkiteTyrantControlEffect(final HellkiteTyrantControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public HellkiteTyrantControlEffect copy() {
        return new HellkiteTyrantControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && controllerId != null) {
            return permanent.changeControllerId(controllerId, game);
        }
        return false;
    }
}
