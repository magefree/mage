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
package mage.cards.m;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class MechanizedProduction extends CardImpl {

    public MechanizedProduction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add("Aura");

        // Enchant artifact you control
        TargetPermanent auraTarget = new TargetControlledPermanent(new FilterControlledArtifactPermanent());
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your upkeep, create a token that's a copy of enchanted artifact. Then if you control eight or more artifacts with the same name as one another, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MechanizedProductionEffect(), TargetController.YOU, false));
    }

    public MechanizedProduction(final MechanizedProduction card) {
        super(card);
    }

    @Override
    public MechanizedProduction copy() {
        return new MechanizedProduction(this);
    }
}

class MechanizedProductionEffect extends OneShotEffect {

    public MechanizedProductionEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a token that's a copy of enchanted artifact. Then if you control eight or more artifacts with the same name as one another, you win the game";
    }

    public MechanizedProductionEffect(final MechanizedProductionEffect effect) {
        super(effect);
    }

    @Override
    public MechanizedProductionEffect copy() {
        return new MechanizedProductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null && sourceObject.getAttachedTo() != null) {
            Permanent enchantedArtifact = game.getPermanentOrLKIBattlefield(sourceObject.getAttachedTo());
            if (enchantedArtifact != null) {
                EmptyToken token = new EmptyToken();
                CardUtil.copyTo(token).from(enchantedArtifact);
                token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            }
            Map<String, Integer> countNames = new HashMap<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), source.getControllerId(), game)) {
                int counter = countNames.getOrDefault(permanent.getName(),0);
                countNames.put(permanent.getName(), counter + 1);
            }
            for (Entry<String, Integer> entry : countNames.entrySet()) {
                if (entry.getValue() > 7) {
                    Player controller = game.getPlayer(source.getControllerId());
                    game.informPlayers(controller.getLogName() + " controls eight or more artifacts with the same name as one another (" + entry.getKey() + ").");
                    controller.won(game);
                    return true;
                }
            }
            return true;

        }
        return false;
    }
}
