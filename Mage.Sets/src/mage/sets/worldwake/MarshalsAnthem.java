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
package mage.sets.worldwake;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.common.EmptyEffect;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 *
 */
public class MarshalsAnthem extends CardImpl<MarshalsAnthem> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    protected static final String rule = "return up to X target creature cards from your graveyard to the battlefield, where X is the number of times Marshal's Anthem was kicked";

    public MarshalsAnthem(UUID ownerId) {
        super(ownerId, 15, "Marshal's Anthem", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "WWK";

        this.color.setWhite(true);

        // Multikicker {1}{W}
        MultikickerAbility ability = new MultikickerAbility(new EmptyEffect(rule), false);
        ability.addManaCost(new ManaCostsImpl("{1}{W}"));
        this.addAbility(ability);

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Constants.Duration.WhileOnBattlefield, filter, false)));

        // When Marshal's Anthem enters the battlefield, return up to X target creature cards from your graveyard to the battlefield, where X is the number of times Marshal's Anthem was kicked.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MarshalsAnthemEffect()));
    }

    public MarshalsAnthem(final MarshalsAnthem card) {
        super(card);
    }

    @Override
    public MarshalsAnthem copy() {
        return new MarshalsAnthem(this);
    }
}

class MarshalsAnthemEffect extends OneShotEffect<MarshalsAnthemEffect> {

    public MarshalsAnthemEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        this.staticText = "return up to X target creature cards from your graveyard to the battlefield, where X is the number of times {this} was kicked";
    }

    public MarshalsAnthemEffect(final MarshalsAnthemEffect effect) {
        super(effect);
    }

    @Override
    public MarshalsAnthemEffect copy() {
        return new MarshalsAnthemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterCard("creature card in your graveyard");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        Player you = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            for (Ability ability : permanent.getAbilities()) {
                if (ability instanceof MultikickerAbility) {
                    int count = Math.min(you.getGraveyard().size(), ((MultikickerAbility) ability).getActivateCount());
                    TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, count, filter);
                    if (you != null) {
                        if (target.canChoose(source.getControllerId(), game) && target.choose(Constants.Outcome.Neutral, source.getControllerId(), source.getId(), game)) {
                            if (!target.getTargets().isEmpty()) {
                                List<UUID> targets = target.getTargets();
                                for (UUID targetId : targets) {
                                    Card card = game.getCard(targetId);
                                    if (card != null) {
                                        card.putOntoBattlefield(game, Constants.Zone.GRAVEYARD, source.getId(), you.getId());
                                    }
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
