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
package mage.cards.c;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public class CustodiSoulcaller extends CardImpl {

    public CustodiSoulcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Melee
        this.addAbility(new MeleeAbility());

        // Whenever Custodi Soulcaller attacks, return target creature card with converted mana cost X or less from your graveyard to the battlefield, where X is the number of players you attacked with a creature this combat.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        ability.addWatcher(new CustodiSoulcallerWatcher());
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card with converted mana cost X or less from your graveyard, where X is the number of players you attacked with a creature this combat")));
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getClass().equals(AttacksTriggeredAbility.class)) {
            ability.getTargets().clear();
            CustodiSoulcallerWatcher watcher = (CustodiSoulcallerWatcher) game.getState().getWatchers().get(CustodiSoulcallerWatcher.class.getSimpleName());
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(ability.getSourceId());
            if (watcher != null && watcher.playersAttacked != null) {
                int xValue = watcher.getNumberOfAttackedPlayers(sourcePermanent.getControllerId());
                FilterCard filter = new FilterCard("creature card with converted mana cost " + xValue + " or less");
                filter.add(new CardTypePredicate(CardType.CREATURE));
                filter.add(Predicates.or(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue), new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, xValue)));
                ability.getTargets().add(new TargetCardInYourGraveyard(filter));
            }
        }
    }

    public CustodiSoulcaller(final CustodiSoulcaller card) {
        super(card);
    }

    @Override
    public CustodiSoulcaller copy() {
        return new CustodiSoulcaller(this);
    }
}

class CustodiSoulcallerWatcher extends Watcher {

    protected final HashMap<UUID, Set<UUID>> playersAttacked = new HashMap<>(0);

    CustodiSoulcallerWatcher() {
        super("CustodiSoulcallerWatcher", WatcherScope.GAME);
    }

    CustodiSoulcallerWatcher(final CustodiSoulcallerWatcher watcher) {
        super(watcher);
        this.playersAttacked.putAll(watcher.playersAttacked);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.BEGIN_COMBAT_STEP_PRE) {
            this.playersAttacked.clear();
        }
        else if (event.getType() == EventType.ATTACKER_DECLARED) {
            Set<UUID> attackedPlayers = this.playersAttacked.getOrDefault(event.getPlayerId(), new HashSet<>(1));
            attackedPlayers.add(event.getTargetId());
            this.playersAttacked.put(event.getPlayerId(), attackedPlayers);
        }
    }

    public int getNumberOfAttackedPlayers(UUID attackerId) {
        return this.playersAttacked.get(attackerId).size();
    }

    @Override
    public CustodiSoulcallerWatcher copy() {
        return new CustodiSoulcallerWatcher(this);
    }
}
