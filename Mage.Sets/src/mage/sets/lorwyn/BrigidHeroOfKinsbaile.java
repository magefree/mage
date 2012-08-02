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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class BrigidHeroOfKinsbaile extends CardImpl<BrigidHeroOfKinsbaile> {

    public BrigidHeroOfKinsbaile(UUID ownerId) {
        super(ownerId, 6, "Brigid, Hero of Kinsbaile", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Legendary");
        this.subtype.add("Kithkin");
        this.subtype.add("Archer");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {tap}: Brigid, Hero of Kinsbaile deals 2 damage to each attacking or blocking creature target player controls.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BrigidHeroOfKinsbaileEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public BrigidHeroOfKinsbaile(final BrigidHeroOfKinsbaile card) {
        super(card);
    }

    @Override
    public BrigidHeroOfKinsbaile copy() {
        return new BrigidHeroOfKinsbaile(this);
    }
}

class BrigidHeroOfKinsbaileEffect extends OneShotEffect<BrigidHeroOfKinsbaileEffect> {

    private static final FilterPermanent filter = new FilterPermanent("each attacking or blocking creature target player controls");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.or(
                new AttackingPredicate(),
                new BlockingPredicate()));

    }

    public BrigidHeroOfKinsbaileEffect() {
        super(Constants.Outcome.Damage);
        staticText = "{this} deals 2 damage to each attacking or blocking creature target player controls";
    }

    public BrigidHeroOfKinsbaileEffect(final BrigidHeroOfKinsbaileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, targetPlayer.getId(), game)) {
                if (creature != null) {
                    creature.damage(2, id, game, false, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public BrigidHeroOfKinsbaileEffect copy() {
        return new BrigidHeroOfKinsbaileEffect(this);
    }
}
