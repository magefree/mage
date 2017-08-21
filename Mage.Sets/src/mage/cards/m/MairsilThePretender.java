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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.CounterCardPredicate;
import mage.filter.predicate.other.OwnerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public class MairsilThePretender extends CardImpl {

    public MairsilThePretender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Mairsil, the Pretender enters the battlefield, you may exile an artifact or creature card from your hand or graveyard and put a cage counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MairsilThePretenderExileEffect(), true));

        // Mairsil, the Pretender has all activated abilities of all cards you own in exile with cage counters on them. You may activate each of those abilities only once each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MairsilThePretenderGainAbilitiesEffect()));
    }

    public MairsilThePretender(final MairsilThePretender card) {
        super(card);
    }

    @Override
    public MairsilThePretender copy() {
        return new MairsilThePretender(this);
    }
}

class MairsilThePretenderExileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE)));
    }

    MairsilThePretenderExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }

    MairsilThePretenderExileEffect(final MairsilThePretenderExileEffect effect) {
        super(effect);
    }

    @Override
    public MairsilThePretenderExileEffect copy() {
        return new MairsilThePretenderExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.PutCardInPlay, "Exile a card from your hand? (No = from graveyard)", source, game)) {
                Target target = new TargetCardInHand(0, 1, filter);
                controller.choose(outcome, target, source.getSourceId(), game);
                Card card = controller.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.EXILED, source, game);
                    card.addCounters(CounterType.CAGE.createInstance(), source, game);
                }
            } else {
                Target target = new TargetCardInYourGraveyard(0, 1, filter);
                target.choose(Outcome.PutCardInPlay, source.getControllerId(), source.getSourceId(), game);
                Card card = controller.getGraveyard().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.EXILED, source, game);
                    card.addCounters(CounterType.CAGE.createInstance(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}

class MairsilThePretenderGainAbilitiesEffect extends ContinuousEffectImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new CounterCardPredicate(CounterType.CAGE));
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public MairsilThePretenderGainAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all cards you own in exile with cage counters on them. You may activate each of those abilities only once each turn";
    }

    public MairsilThePretenderGainAbilitiesEffect(final MairsilThePretenderGainAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null) {
            for (Card card : game.getExile().getAllCards(game)) {
                if (filter.match(card, game)) {
                    for (Ability ability : card.getAbilities()) {
                        if (ability instanceof ActivatedAbility) {
                            ActivatedAbility copyAbility = (ActivatedAbility) ability.copy();
                            perm.addAbility(copyAbility, card.getId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MairsilThePretenderGainAbilitiesEffect copy() {
        return new MairsilThePretenderGainAbilitiesEffect(this);
    }
}
