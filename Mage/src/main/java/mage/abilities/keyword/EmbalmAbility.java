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
package mage.abilities.keyword;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class EmbalmAbility extends ActivatedAbilityImpl {

    private String rule;

    public EmbalmAbility(Cost cost, Card card) {
        super(Zone.GRAVEYARD, new EmbalmEffect(), cost);
        addCost(new ExileSourceFromGraveCost());
        this.rule = setRule(cost, card);
        this.timing = TimingRule.SORCERY;
        setRule(cost, card);
    }

    public EmbalmAbility(final EmbalmAbility ability) {
        super(ability);
        this.rule = ability.rule;
    }

    @Override
    public EmbalmAbility copy() {
        return new EmbalmAbility(this);
    }

    @Override
    public String getRule() {
        return rule;
    }

    private String setRule(Cost cost, Card card) {
        StringBuilder sb = new StringBuilder("Embalm ").append(cost.getText());
        sb.append(" <i>(").append(cost.getText());
        sb.append(", Exile this card from your graveyard: Create a token that's a copy of it, except it's a white Zombie ");
        for (String subtype : card.getSubtype(null)) {
            sb.append(subtype).append(" ");
        }
        sb.append(" with no mana cost. Embalm only as a sorcery.)</i>");
        return sb.toString();
    }
}

class EmbalmEffect extends OneShotEffect {

    public EmbalmEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public EmbalmEffect(final EmbalmEffect effect) {
        super(effect);
    }

    @Override
    public EmbalmEffect copy() {
        return new EmbalmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(card); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
            token.getColor(game).setColor(ObjectColor.WHITE);
            if (!token.getSubtype(game).contains("Zombie")) {
                token.getSubtype(game).add(0, "Zombie");
            }
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), false, false, null);
            return true;
        }

        return false;
    }

}
