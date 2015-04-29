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
package mage.sets.riseoftheeldrazi;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;


/**
 * @author magenoxx_at_gmail.com
 */
public class CastThroughTime extends CardImpl {

    protected static final FilterCard filter = new FilterCard("Instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public CastThroughTime(UUID ownerId) {
        super(ownerId, 55, "Cast Through Time", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}{U}");
        this.expansionSetCode = "ROE";

        // Instant and sorcery spells you control have rebound.
        //  (Exile the spell as it resolves if you cast it from your hand. At the beginning of your next upkeep, you may cast that card from exile without paying its mana cost.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainReboundEffect()));
    }

    public CastThroughTime(final CastThroughTime card) {
        super(card);
    }

    @Override
    public CastThroughTime copy() {
        return new CastThroughTime(this);
    }
}

class GainReboundEffect extends ContinuousEffectImpl {

    public GainReboundEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Instant and sorcery spells you control have rebound  <i>(Exile the spell as it resolves if you cast it from your hand. At the beginning of your next upkeep, you may cast that card from exile without paying its mana cost.)</i>";
    }

    public GainReboundEffect(final GainReboundEffect effect) {
        super(effect);
    }

    @Override
    public GainReboundEffect copy() {
        return new GainReboundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            for (Card card : player.getHand().getCards(CastThroughTime.filter, game)) {
                addReboundAbility(card, source, game);
            }
            for (Iterator<StackObject> iterator = game.getStack().iterator(); iterator.hasNext();) {
                StackObject stackObject = iterator.next();
                if (stackObject instanceof Spell && stackObject.getControllerId().equals(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    Card card = spell.getCard();
                    if (card != null) {
                        addReboundAbility(card, source, game);
                    }
                    
                }                
            }
            return true;
        }
        return false;
    }

    private void addReboundAbility(Card card, Ability source, Game game) {
        if (CastThroughTime.filter.match(card, game)) {
            boolean found = false;
            for (Ability ability : card.getAbilities()) {
                if (ability instanceof ReboundAbility) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                Ability ability = new ReboundAbility();
                game.getState().addOtherAbility(card, ability);
            }
        }
    }
}
