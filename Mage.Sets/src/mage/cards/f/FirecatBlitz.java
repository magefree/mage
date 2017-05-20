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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElementalCatToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public class FirecatBlitz extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Mountains");

    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public FirecatBlitz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Put X 1/1 red Elemental Cat creature tokens with haste onto the battlefield. Exile them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new FirecatBlitzEffect());

        // Flashback-{R}{R}, Sacrifice X Mountains.
        Ability ability = new FlashbackAbility(new SacrificeXTargetCost(filter), TimingRule.SORCERY);
        ability.addManaCost(new ManaCostsImpl("{R}{R}"));
        this.addAbility(ability);
    }

    public FirecatBlitz(final FirecatBlitz card) {
        super(card);
    }

    @Override
    public FirecatBlitz copy() {
        return new FirecatBlitz(this);
    }
}

class FirecatBlitzEffect extends OneShotEffect {

    public FirecatBlitzEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create X 1/1 red Elemental Cat creature tokens with haste. Exile them at the beginning of the next end step";
    }

    public FirecatBlitzEffect(final FirecatBlitzEffect effect) {
        super(effect);
    }

    @Override
    public FirecatBlitzEffect copy() {
        return new FirecatBlitzEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = source.getManaCostsToPay().getX();
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    xValue = ((SacrificeTargetCost) cost).getPermanents().size();
                }
            }
            CreateTokenEffect effect = new CreateTokenEffect(new ElementalCatToken(), xValue);
            effect.apply(game, source);
            for (UUID tokenId : effect.getLastAddedTokenIds()) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
                    exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
                }
            }
            return true;
        }

        return false;
    }
}
