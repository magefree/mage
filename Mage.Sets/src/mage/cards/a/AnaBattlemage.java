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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox

 */
public class AnaBattlemage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature");
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public AnaBattlemage(UUID ownerId) {
        super(ownerId, 124, "Ana Battlemage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {2}{U} and/or {1}{B}
        KickerAbility kickerAbility = new KickerAbility("{2}{U}");
        kickerAbility.addKickerCost("{1}{B}");
        this.addAbility(kickerAbility);
        // When Ana Battlemage enters the battlefield, if it was kicked with its {2}{U} kicker, target player discards three cards.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(3));
        ability.addTarget(new TargetOpponent());
        this.addAbility(new ConditionalTriggeredAbility(ability, new KickedCostCondition("{2}{U}"),
            "When {this} enters the battlefield, if it was kicked with its {2}{U} kicker, target player discards three cards."));
        // When Ana Battlemage enters the battlefield, if it was kicked with its {1}{B} kicker, tap target untapped creature and that creature deals damage equal to its power to its controller.
        ability = new EntersBattlefieldTriggeredAbility(new AnaBattlemageKickerEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(new ConditionalTriggeredAbility(ability, new KickedCostCondition("{1}{B}"),
            "When {this} enters the battlefield, if it was kicked with its {1}{B} kicker, tap target untapped creature and that creature deals damage equal to its power to its controller."));
    }

    public AnaBattlemage(final AnaBattlemage card) {
        super(card);
    }

    @Override
    public AnaBattlemage copy() {
        return new AnaBattlemage(this);
    }
}

class AnaBattlemageKickerEffect extends OneShotEffect {

    public AnaBattlemageKickerEffect() {
        super(Outcome.Detriment);
        this.staticText = "tap target untapped creature and it deals damage equal to its power to its controller";
    }

    public AnaBattlemageKickerEffect(final AnaBattlemageKickerEffect effect) {
        super(effect);
    }

    @Override
    public AnaBattlemageKickerEffect copy() {
        return new AnaBattlemageKickerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature != null) {
            applied = targetCreature.tap(game);
            Player controller = game.getPlayer(targetCreature.getControllerId());
            if (controller != null) {
                controller.damage(targetCreature.getPower().getValue(), source.getSourceId(), game, false, true);
                applied = true;
            }
        }
        return applied;
    }
}
