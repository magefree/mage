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
package mage.sets.morningtide;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetDefender;

/**
 * 
 * @author Rafbill
 */
public class PreeminentCaptain extends CardImpl<PreeminentCaptain> {

    public PreeminentCaptain(UUID ownerId) {
        super(ownerId, 20, "Preeminent Captain", Rarity.RARE,
                new CardType[] { CardType.CREATURE }, "{2}{W}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Kithkin");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        // Whenever Preeminent Captain attacks, you may put a Soldier creature
        // card from your hand onto the battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new PreeminentCaptainEffect(), true));
    }

    public PreeminentCaptain(final PreeminentCaptain card) {
        super(card);
    }

    @Override
    public PreeminentCaptain copy() {
        return new PreeminentCaptain(this);
    }
}

class PreeminentCaptainEffect extends OneShotEffect<PreeminentCaptainEffect> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a soldier creature card");

    static {
        filter.add(new SubtypePredicate("Soldier"));
    }

    public PreeminentCaptainEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "put a Soldier creature card from your hand onto the battlefield tapped and attacking.";
    }

    public PreeminentCaptainEffect(final PreeminentCaptainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        TargetCardInHand target = new TargetCardInHand(filter);
        if (target.canChoose(player.getId(), game) && target.choose(getOutcome(), player.getId(), source.getSourceId(), game)) {
            if (target.getTargets().size() > 0) {
                UUID cardId = target.getFirstTarget();
                Card card = player.getHand().get(cardId, game);
                if (card != null) {
                    if (card.putOntoBattlefield(game, Zone.HAND, source.getId(), source.getControllerId())) {
                        Permanent permanent = game.getPermanent(card.getId());
                        permanent.setTapped(true);
                        game.getCombat().addAttackingCreature(permanent.getId(), game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public PreeminentCaptainEffect copy() {
        return new PreeminentCaptainEffect(this);
    }

}
