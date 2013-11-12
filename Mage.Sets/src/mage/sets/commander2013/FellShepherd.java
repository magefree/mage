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
package mage.sets.commander2013;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class FellShepherd extends CardImpl<FellShepherd> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");
    static {
        filter.add(new AnotherPredicate());
    }

    public FellShepherd(UUID ownerId) {
        super(ownerId, 78, "Fell Shepherd", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.expansionSetCode = "C13";
        this.subtype.add("Avatar");

        this.color.setBlack(true);
        this.power = new MageInt(8);
        this.toughness = new MageInt(6);

        // Whenever Fell Shepherd deals combat damage to a player, you may return to your hand all creature cards that were put into your graveyard from the battlefield this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new FellShepherdEffect(), true));
        this.addWatcher(new FellShepherdWatcher());

        // {B}, Sacrifice another creature: Target creature gets -2/-2 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-2,-2, Duration.EndOfTurn), new ManaCostsImpl("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        ability.addTarget(new TargetCreaturePermanent(true));
        this.addAbility(ability);

    }

    public FellShepherd(final FellShepherd card) {
        super(card);
    }

    @Override
    public FellShepherd copy() {
        return new FellShepherd(this);
    }
}

class FellShepherdWatcher extends WatcherImpl<FellShepherdWatcher> {

    private Set<UUID> creatureIds = new HashSet<UUID>();

    public FellShepherdWatcher() {
        super("YourCreaturesPutToGraveFromBattlefield", WatcherScope.PLAYER);
        condition = true;
    }

    public FellShepherdWatcher(final FellShepherdWatcher watcher) {
        super(watcher);
        this.creatureIds.addAll(watcher.creatureIds);
    }

    @Override
    public FellShepherdWatcher copy() {
        return new FellShepherdWatcher(this);
    }

    public Set<UUID> getCreaturesIds() {
        return creatureIds;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject card = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && ((Card)card).getOwnerId().equals(this.controllerId) && card.getCardType().contains(CardType.CREATURE)) {
                creatureIds.add(card.getId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatureIds.clear();
    }
}

class FellShepherdEffect extends OneShotEffect<FellShepherdEffect> {

    public FellShepherdEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to your hand all creature cards that were put into your graveyard from the battlefield this turn";
    }

    public FellShepherdEffect(final FellShepherdEffect effect) {
        super(effect);
    }

    @Override
    public FellShepherdEffect copy() {
        return new FellShepherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FellShepherdWatcher watcher = (FellShepherdWatcher) game.getState().getWatchers().get("YourCreaturesPutToGraveFromBattlefield", source.getControllerId());
        if (watcher != null) {
            StringBuilder sb = new StringBuilder();
            for (UUID creatureId : watcher.getCreaturesIds()) {
                if (game.getState().getZone(creatureId).equals(Zone.GRAVEYARD)) {
                    Card card = game.getCard(creatureId);
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                        sb.append(" ").append(card.getName());
                    }
                }
            }
            if (sb.length()> 0) {
                sb.insert(0, "Fell Shepherd - returning to hand:");
                game.informPlayers(sb.toString());
            }
            return true;
        }
        return false;
    }
}
