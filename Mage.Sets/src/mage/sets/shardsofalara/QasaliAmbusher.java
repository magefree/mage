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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class QasaliAmbusher extends CardImpl<QasaliAmbusher> {

    public QasaliAmbusher(UUID ownerId) {
        super(ownerId, 184, "Qasali Ambusher", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Cat");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // If a creature is attacking you and you control a Forest and a Plains, you may casbt Qasali Ambusher without paying its mana cost and as though it had flash.
        this.addAbility(new QasaliAmbusherAbility());

    }

    public QasaliAmbusher(final QasaliAmbusher card) {
        super(card);
    }

    @Override
    public QasaliAmbusher copy() {
        return new QasaliAmbusher(this);
    }
}

class QasaliAmbusherAbility extends ActivatedAbilityImpl<QasaliAmbusherAbility> {

    private static final FilterControlledLandPermanent filterPlains = new FilterControlledLandPermanent();
    private static final FilterControlledLandPermanent filterForest = new FilterControlledLandPermanent();

    static {
        filterPlains.add(new SubtypePredicate("Plains"));
        filterForest.add(new SubtypePredicate("Forest"));
    }

    public QasaliAmbusherAbility() {
        super(Zone.HAND, new QasaliAmbusherEffect(), new ManaCostsImpl());
        this.timing = TimingRule.INSTANT;
        this.usesStack = false;
    }

    public QasaliAmbusherAbility(final QasaliAmbusherAbility ability) {
        super(ability);
    }

    @Override
    public QasaliAmbusherAbility copy() {
        return new QasaliAmbusherAbility(this);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game)) {
            if (game.getBattlefield().getActivePermanents(filterPlains, this.getControllerId(), this.getSourceId(), game).size() > 0
                    && game.getBattlefield().getActivePermanents(filterForest, this.getControllerId(), this.getSourceId(), game).size() > 0) {
                for (CombatGroup group : game.getCombat().getGroups()) {
                    if (group.getDefenderId().equals(getControllerId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        return "If a creature is attacking you and you control a Forest and a Plains, you may cast {this} without paying its mana cost and as though it had flash.";
    }
}

class QasaliAmbusherEffect extends OneShotEffect<QasaliAmbusherEffect> {

    public QasaliAmbusherEffect() {
        super(Outcome.Benefit);
        staticText = "";
    }

    public QasaliAmbusherEffect(final QasaliAmbusherEffect effect) {
        super(effect);
    }

    @Override
    public QasaliAmbusherEffect copy() {
        return new QasaliAmbusherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) game.getObject(source.getSourceId());
        if (card != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                SpellAbility spellAbility = card.getSpellAbility();
                spellAbility.clear();
                return controller.cast(spellAbility, game, true);
            }
        }
        return false;
    }
}
