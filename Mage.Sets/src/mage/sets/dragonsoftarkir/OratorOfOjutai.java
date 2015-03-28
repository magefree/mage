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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public class OratorOfOjutai extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate("Dragon"));
    }

    public OratorOfOjutai(UUID ownerId) {
        super(ownerId, 28, "Orator of Ojutai", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Bird");
        this.subtype.add("Monk");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender, flying
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        // As an additional cost to cast Orator of Ojutai, you may reveal a Dragon card from your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("As an additional cost to cast {this}, you may reveal a Dragon card from your hand")));

        // When Orator of Ojutai enters the battlefield, if you revealed a Dragon card or controlled a Dragon as you cast Orator of Ojutai, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OratorOfOjutaiEffect()), new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getAbilityType().equals(AbilityType.SPELL)) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                if (controller.getHand().count(filter, game) > 0) {
                    ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0,1, filter)));
                }
            }
        }
    }

    public OratorOfOjutai(final OratorOfOjutai card) {
        super(card);
    }

    @Override
    public OratorOfOjutai copy() {
        return new OratorOfOjutai(this);
    }
}

class OratorOfOjutaiEffect extends OneShotEffect {

    public OratorOfOjutaiEffect() {
        super(Outcome.Benefit);
        this.staticText = "If you revealed a Dragon card or controlled a Dragon as you cast {this}, draw a card";
    }

    public OratorOfOjutaiEffect(final OratorOfOjutaiEffect effect) {
        super(effect);
    }

    @Override
    public OratorOfOjutaiEffect copy() {
        return new OratorOfOjutaiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent != null) {
                DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = (DragonOnTheBattlefieldWhileSpellWasCastWatcher) game.getState().getWatchers().get("DragonOnTheBattlefieldWhileSpellWasCastWatcher");
                if (watcher != null && watcher.castWithConditionTrue(sourcePermanent.getSpellAbility().getId())) {
                    controller.drawCards(1, game);
                }
            }
            return true;
        }
        return false;
    }
}
