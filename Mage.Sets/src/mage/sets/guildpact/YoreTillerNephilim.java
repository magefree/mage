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
package mage.sets.guildpact;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public class YoreTillerNephilim extends CardImpl {

    public YoreTillerNephilim(UUID ownerId) {
        super(ownerId, 140, "Yore-Tiller Nephilim", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Nephilim");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Yore-Tiller Nephilim attacks, return target creature card from your graveyard to the battlefield tapped and attacking.
        Ability ability = new AttacksTriggeredAbility(new YoreTillerNephilimEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard()));
        this.addAbility(ability);
    }

    public YoreTillerNephilim(final YoreTillerNephilim card) {
        super(card);
    }

    @Override
    public YoreTillerNephilim copy() {
        return new YoreTillerNephilim(this);
    }
}

class YoreTillerNephilimEffect extends OneShotEffect {

    public YoreTillerNephilimEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card from your graveyard to the battlefield tapped and attacking";
    }

    public YoreTillerNephilimEffect(final YoreTillerNephilimEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                if (card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), source.getControllerId(), true)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    game.getCombat().addAttackingCreature(permanent.getId(), game);
                }
            }
            return true;

        }
        return false;
    }

    @Override
    public YoreTillerNephilimEffect copy() {
        return new YoreTillerNephilimEffect(this);
    }
}
