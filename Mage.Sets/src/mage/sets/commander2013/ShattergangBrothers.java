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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class ShattergangBrothers extends CardImpl<ShattergangBrothers> {

    public ShattergangBrothers(UUID ownerId) {
        super(ownerId, 213, "Shattergang Brothers", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Goblin");
        this.subtype.add("Artificer");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{B}, Sacrifice a creature: Each other player sacrifices a creature.
        FilterControlledCreaturePermanent filterCreature = new FilterControlledCreaturePermanent("a creature");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(filterCreature), new ManaCostsImpl("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, filterCreature, true, true)));
        this.addAbility(ability);
        // {2}{R}, Sacrifice an artifact: Each other player sacrifices an artifact.
        FilterControlledPermanent filter = new FilterControlledArtifactPermanent("an artifact");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(filter), new ManaCostsImpl("{2}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1,1, filter, true)));
        this.addAbility(ability);
        // {2}{G}, Sacrifice an enchantment: Each other player sacrifices an enchantment.
        filter = new FilterControlledPermanent("an enchantment");
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(filter), new ManaCostsImpl("{2}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1,1, filter, true)));
        this.addAbility(ability);
    }

    public ShattergangBrothers(final ShattergangBrothers card) {
        super(card);
    }

    @Override
    public ShattergangBrothers copy() {
        return new ShattergangBrothers(this);
    }
}

class ShattergangBrothersEffect extends OneShotEffect<ShattergangBrothersEffect> {

    private FilterControlledPermanent filter;

    public ShattergangBrothersEffect(FilterControlledPermanent filter) {
        super(Outcome.Sacrifice);
        this.filter = filter;
        this.staticText = new StringBuilder("Each other player sacrifices ").append(filter.getMessage()).toString();
    }

    public ShattergangBrothersEffect(final ShattergangBrothersEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public ShattergangBrothersEffect copy() {
        return new ShattergangBrothersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for(UUID playerId : controller.getInRange()) {
                if (playerId != source.getControllerId()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        TargetControlledPermanent target = new TargetControlledPermanent(filter);
                        target.setRequired(true);
                        target.setNotTarget(true);
                        if (target.canChoose(source.getSourceId(), playerId, game) &&
                                player.chooseTarget(outcome, target, source, game)) {
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                permanent.sacrifice(source.getSourceId(), game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
