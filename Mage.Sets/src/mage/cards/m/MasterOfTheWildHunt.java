/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MasterOfTheWildHunt extends CardImpl {

    private static WolfToken wolfToken = new WolfToken();

    public MasterOfTheWildHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, create a 2/2 green Wolf creature token.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CreateTokenEffect(wolfToken)));

        // {T}: Tap all untapped Wolf creatures you control. Each Wolf tapped this way deals damage equal to its power to target creature. That creature deals damage equal to its power divided as its controller chooses among any number of those Wolves.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MasterOfTheWildHuntEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public MasterOfTheWildHunt(final MasterOfTheWildHunt card) {
        super(card);
    }

    @Override
    public MasterOfTheWildHunt copy() {
        return new MasterOfTheWildHunt(this);
    }
}

class MasterOfTheWildHuntEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new SubtypePredicate(SubType.WOLF));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public MasterOfTheWildHuntEffect() {
        super(Outcome.Damage);
        staticText = "Tap all untapped Wolf creatures you control. Each Wolf tapped this way deals damage equal to its power to target creature. That creature deals damage equal to its power divided as its controller chooses among any number of those Wolves";
    }

    public MasterOfTheWildHuntEffect(final MasterOfTheWildHuntEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfTheWildHuntEffect copy() {
        return new MasterOfTheWildHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> wolves = new ArrayList<>();
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null && game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                permanent.tap(game);
                target.damage(permanent.getToughness().getValue(), permanent.getId(), game, false, true);
                wolves.add(permanent.getId());
            }
            Player player = game.getPlayer(target.getControllerId());
            player.assignDamage(target.getPower().getValue(), wolves, "Wolf", target.getId(), game);
            return true;
        }
        return false;
    }

}
