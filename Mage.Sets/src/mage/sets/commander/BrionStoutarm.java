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
package mage.sets.commander;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class BrionStoutarm extends CardImpl<BrionStoutarm> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature other than Brion Stoutarm");

    static {
        filter.add(new AnotherPredicate());
    }

    public BrionStoutarm(UUID ownerId) {
        super(ownerId, 187, "Brion Stoutarm", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.expansionSetCode = "CMD";
        this.supertype.add("Legendary");
        this.subtype.add("Giant");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // {R}, {tap}, Sacrifice a creature other than Brion Stoutarm: Brion Stoutarm deals damage equal to the sacrificed creature's power to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BrionStoutarmEffect(), new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, filter, true)));
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);
    }

    public BrionStoutarm(final BrionStoutarm card) {
        super(card);
    }

    @Override
    public BrionStoutarm copy() {
        return new BrionStoutarm(this);
    }
}

class BrionStoutarmEffect extends OneShotEffect<BrionStoutarmEffect> {

    public BrionStoutarmEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage equal to the sacrificed creature's power to target player";
    }

    public BrionStoutarmEffect(final BrionStoutarmEffect effect) {
        super(effect);
    }

    @Override
    public BrionStoutarmEffect copy() {
        return new BrionStoutarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost: source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && ((SacrificeTargetCost)cost).getPermanents().size() > 0) {
                amount = ((SacrificeTargetCost)cost).getPermanents().get(0).getPower().getValue();
                break;
            }
        }
        if (amount > 0) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
