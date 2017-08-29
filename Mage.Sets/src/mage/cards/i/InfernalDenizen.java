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
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class InfernalDenizen extends CardImpl {

    public InfernalDenizen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}");

        this.subtype.add("Demon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // At the beginning of your upkeep, sacrifice two Swamps. If you can't, tap Infernal Denizen, and an opponent may gain control of a creature you control of his or her choice for as long as Infernal Denizen remains on the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InfernalDenizenEffect(), TargetController.YOU, false));

        // {tap}: Gain control of target creature for as long as Infernal Denizen remains on the battlefield.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom, true),
                SourceOnBattlefieldCondition.instance,
                "gain control of target creature for as long as {this} remains on the battlefield");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public InfernalDenizen(final InfernalDenizen card) {
        super(card);
    }

    @Override
    public InfernalDenizen copy() {
        return new InfernalDenizen(this);
    }
}

class InfernalDenizenEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new SubtypePredicate(SubType.SWAMP));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    InfernalDenizenEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice two Swamps. If you can't, tap {this}, "
                + "and an opponent may gain control of a creature you control of his or her choice "
                + "for as long as {this} remains on the battlefield";
    }

    InfernalDenizenEffect(final InfernalDenizenEffect effect) {
        super(effect);
    }

    @Override
    public InfernalDenizenEffect copy() {
        return new InfernalDenizenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            DynamicValue swamps = new PermanentsOnBattlefieldCount(filter);
            boolean canSac = swamps.calculate(game, source, this) > 1;
            Effect effect = new SacrificeControllerEffect(filter, 2, "Sacrifice two Swamps");
            effect.apply(game, source);
            if (!canSac) {
                if (creature != null) {
                    creature.tap(game);
                }
                TargetOpponent targetOpp = new TargetOpponent(true);
                if (targetOpp.canChoose(player.getId(), game)
                        && targetOpp.choose(Outcome.Detriment, player.getId(), source.getSourceId(), game)) {
                    Player opponent = game.getPlayer(targetOpp.getFirstTarget());
                    if (opponent != null) {
                        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature controlled by " + player.getLogName());
                        filter2.add(new ControllerIdPredicate(player.getId()));
                        TargetCreaturePermanent targetCreature = new TargetCreaturePermanent(1, 1, filter2, true);
                        targetCreature.setTargetController(opponent.getId());
                        if (targetCreature.canChoose(id, game)
                                && opponent.chooseUse(Outcome.GainControl, "Gain control of a creature?", source, game)
                                && opponent.chooseTarget(Outcome.GainControl, targetCreature, source, game)) {
                            ConditionalContinuousEffect giveEffect = new ConditionalContinuousEffect(
                                    new GainControlTargetEffect(Duration.Custom, true, opponent.getId()),
                                    SourceOnBattlefieldCondition.instance,
                                    "");
                            giveEffect.setTargetPointer(new FixedTarget(targetCreature.getFirstTarget()));
                            game.addEffect(giveEffect, source);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
