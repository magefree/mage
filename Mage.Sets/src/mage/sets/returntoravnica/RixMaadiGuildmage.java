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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author LevelX2
 */
public class RixMaadiGuildmage extends CardImpl<RixMaadiGuildmage> {

    private static final FilterCreaturePermanent filter = new FilterBlockingCreature("blocking creature");
    private static final FilterPlayer playerFilter = new FilterPlayer("player who lost life this turn");
    static {
        playerFilter.add(new PlayerLostLifePredicate());
    }

    public RixMaadiGuildmage(UUID ownerId) {
        super(ownerId, 192, "Rix Maadi Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{B}{R}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.color.setBlack(true);
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}{R}: Target blocking creature gets -1/-1 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Constants.Duration.EndOfTurn),new ManaCostsImpl("{B}{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {B}{R}: Target player who lost life this turn loses 1 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new ManaCostsImpl("{B}{R}"));
        ability.addTarget(new TargetPlayer(1,1,false, playerFilter));
        this.addAbility(ability);
    }

    public RixMaadiGuildmage(final RixMaadiGuildmage card) {
        super(card);
    }

    @Override
    public RixMaadiGuildmage copy() {
        return new RixMaadiGuildmage(this);
    }
}

class PlayerLostLifePredicate implements Predicate<Player> {

    public PlayerLostLifePredicate() {

    }

    @Override
    public boolean apply(Player input, Game game) {
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get("PlayerLostLifeWatcher");
        if (watcher != null) {
            return (0 < watcher.getLiveLost(input.getId()));
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player lost life";
    }
}