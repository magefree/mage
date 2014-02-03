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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continious.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.turn.Step;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class KioraTheCrashingWave extends CardImpl<KioraTheCrashingWave> {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent control");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public KioraTheCrashingWave(UUID ownerId) {
        super(ownerId, 149, "Kiora, the Crashing Wave", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Kiora");

        this.color.setBlue(true);
        this.color.setGreen(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(2)), false));

        // +1: Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls.
        LoyaltyAbility ability = new LoyaltyAbility(new KioraPreventionEffect(), 1);
        ability.addTarget(new TargetPermanent(filter, true));
        this.addAbility(ability);

        // -1: Draw a card. You may play an additional land this turn.
        ability = new LoyaltyAbility(new DrawCardControllerEffect(1), -1);
        ability.addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));
        this.addAbility(ability);

        // -5: You get an emblem with "At the beginning of your end step, put a 9/9 blue Kraken creature token onto the battlefield."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KioraEmblem()), -5));


    }

    public KioraTheCrashingWave(final KioraTheCrashingWave card) {
        super(card);
    }

    @Override
    public KioraTheCrashingWave copy() {
        return new KioraTheCrashingWave(this);
    }
}

class KioraPreventionEffect extends PreventionEffectImpl<KioraPreventionEffect> {

    public KioraPreventionEffect() {
        super(Duration.Custom);
        staticText = "Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls";
    }

    public KioraPreventionEffect(final KioraPreventionEffect effect) {
        super(effect);
    }

    @Override
    public KioraPreventionEffect copy() {
        return new KioraPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for(UUID targetId :this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addInfo(new StringBuilder("kioraPrevention").append(getId()).toString(),"[All damage that would be dealt to and dealt by this permanent is prevented.]");
            }
        }
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
        if (super.applies(event, source, game) && event instanceof DamageEvent) {
            Permanent targetPermanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (targetPermanent != null 
                    && (event.getSourceId().equals(targetPermanent.getId()) || event.getTargetId().equals(targetPermanent.getId()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE)
        {
            if (game.getActivePlayerId().equals(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                for(UUID targetId :this.getTargetPointer().getTargets(game, source)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.addInfo(new StringBuilder("kioraPrevention").append(getId()).toString(),"");
                    }
                }
                return true;
            }
        }
        return false;
    }
}

/**
 * Emblem: "At the beginning of your end step, put a 9/9 blue Kraken creature token onto the battlefield."
 */
class KioraEmblem extends Emblem {
    public KioraEmblem() {
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, new CreateTokenEffect(new KioraKrakenToken()), TargetController.YOU, null, false);
        this.getAbilities().add(ability);
    }
}

class KioraKrakenToken extends Token {

    public KioraKrakenToken() {
        super("Kraken", "9/9 blue Kraken creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.BLUE;
        subtype.add("Kraken");
        power = new MageInt(9);
        toughness = new MageInt(9);
        this.setOriginalExpansionSetCode("BNG");
    }
}
