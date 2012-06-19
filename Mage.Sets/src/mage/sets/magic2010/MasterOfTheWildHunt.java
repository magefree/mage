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

package mage.sets.magic2010;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MasterOfTheWildHunt extends CardImpl<MasterOfTheWildHunt> {

    private static WolfToken wolfToken = new WolfToken();

    public MasterOfTheWildHunt(UUID ownerId) {
        super(ownerId, 191, "Master of the Wild Hunt", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "M10";
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CreateTokenEffect(wolfToken)));
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

class MasterOfTheWildHuntEffect extends OneShotEffect<MasterOfTheWildHuntEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.getSubtype().add("Wolf");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
        filter.setTapped(false);
        filter.setUseTapped(true);
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
        List<UUID> wolves = new ArrayList<UUID>();
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null && game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
            for (Permanent permanent: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                permanent.tap(game);
                target.damage(permanent.getToughness().getValue(), permanent.getId(), game, true, false);
                wolves.add(permanent.getId());
            }
            Player player = game.getPlayer(target.getControllerId());
            player.assignDamage(target.getPower().getValue(), wolves, "Wolf", target.getId(), game);
            return true;
        }
        return false;
    }

}