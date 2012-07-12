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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author North
 */
public class GremlinMine extends CardImpl<GremlinMine> {

    private static final FilterArtifactPermanent filterCreature = new FilterArtifactPermanent("artifact creature");
    private static final FilterArtifactPermanent filterNonCreature = new FilterArtifactPermanent("noncreature artifact");

    static {
        filterCreature.add(new CardTypePredicate(CardType.CREATURE));
        filterNonCreature.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public GremlinMine(UUID ownerId) {
        super(ownerId, 136, "Gremlin Mine", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "NPH";

        // {1}, {tap}, Sacrifice Gremlin Mine: Gremlin Mine deals 4 damage to target artifact creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filterCreature));
        this.addAbility(ability);
        // {1}, {tap}, Sacrifice Gremlin Mine: Remove up to four charge counters from target noncreature artifact.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GremlinMineEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filterNonCreature));
        this.addAbility(ability);
    }

    public GremlinMine(final GremlinMine card) {
        super(card);
    }

    @Override
    public GremlinMine copy() {
        return new GremlinMine(this);
    }
}

class GremlinMineEffect extends OneShotEffect<GremlinMineEffect> {

    public GremlinMineEffect() {
        super(Outcome.Detriment);
        this.staticText = "Remove up to four charge counters from target noncreature artifact";
    }

    public GremlinMineEffect(GremlinMineEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());

        if (player != null && permanent != null) {
            int existingCount = permanent.getCounters().getCount(CounterType.CHARGE);

            if (existingCount > 0) {
                Choice choice = new ChoiceImpl();
                choice.setMessage("Select number of charge counters to remove:");
                for (Integer i = 0; i <= existingCount; i++) {
                    choice.getChoices().add(i.toString());
                }

                int amount = 0;
                if (player.choose(Outcome.Detriment, choice, game)) {
                    amount = Integer.parseInt(choice.getChoice());
                }

                permanent.removeCounters(CounterType.CHARGE.getName(), amount, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public GremlinMineEffect copy() {
        return new GremlinMineEffect(this);
    }
}
