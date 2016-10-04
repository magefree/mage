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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class DovinBaan extends CardImpl {

    public DovinBaan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{2}{W}{U}");
        this.subtype.add("Dovin");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Until your next turn, up to one target creature gets -3/-0 and its activated abilities can't be activated.
        Effect effect = new BoostTargetEffect(-3, 0, Duration.UntilYourNextTurn);
        effect.setText("Until your next turn, up to one target creature gets -3/-0");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addEffect(new DovinBaanCantActivateAbilitiesEffect());
        this.addAbility(ability);

        // -1: You gain 2 life and draw a card.
        ability = new LoyaltyAbility(new GainLifeEffect(2), -1);
        effect = new DrawCardSourceControllerEffect(1);
        effect.setText("and draw a card");
        ability.addEffect(effect);
        this.addAbility(ability);

        // -7: You get an emblem with "Your opponents can't untap more than two permanents during their untap steps."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new DovinBaanEmblem()), -7));
    }

    public DovinBaan(final DovinBaan card) {
        super(card);
    }

    @Override
    public DovinBaan copy() {
        return new DovinBaan(this);
    }
}

class DovinBaanCantActivateAbilitiesEffect extends ContinuousRuleModifyingEffectImpl {

    DovinBaanCantActivateAbilitiesEffect() {
        super(Duration.UntilYourNextTurn, Outcome.UnboostCreature);
        staticText = "and its activated abilities can't be activated";
    }

    DovinBaanCantActivateAbilitiesEffect(final DovinBaanCantActivateAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public DovinBaanCantActivateAbilitiesEffect copy() {
        return new DovinBaanCantActivateAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
    }
}

class DovinBaanEmblem extends Emblem {

    DovinBaanEmblem() {
        this.setName("EMBLEM: Dovin Baan");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new DovinBaanCantUntapEffect());
        this.getAbilities().add(ability);
    }
}

class DovinBaanCantUntapEffect extends RestrictionUntapNotMoreThanEffect {

    DovinBaanCantUntapEffect() {
        super(Duration.WhileOnBattlefield, 2, new FilterControlledPermanent());
        staticText = "Your opponents can't untap more than two permanents during their untap steps.";
    }

    DovinBaanCantUntapEffect(final DovinBaanCantUntapEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(player.getId());
    }

    @Override
    public DovinBaanCantUntapEffect copy() {
        return new DovinBaanCantUntapEffect(this);
    }
}
