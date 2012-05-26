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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksEquippedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class TrepanationBlade extends CardImpl<TrepanationBlade> {

    public TrepanationBlade(UUID ownerId) {
        super(ownerId, 235, "Trepanation Blade", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Equipment");

        // Whenever equipped creature attacks, defending player reveals cards from the top of his or her library until he or she reveals a land card.
        // The creature gets +1/+0 until end of turn for each card revealed this way. That player puts the revealed cards into his or her graveyard.
        Ability ability = new AttacksEquippedTriggeredAbility(new TrepanationBladeDiscardEffect());
        ability.addEffect(new TrepanationBladeBoostEffect());
        this.addAbility(ability);
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public TrepanationBlade(final TrepanationBlade card) {
        super(card);
    }

    @Override
    public TrepanationBlade copy() {
        return new TrepanationBlade(this);
    }
}

class TrepanationBladeDiscardEffect extends OneShotEffect<TrepanationBladeDiscardEffect> {

    public TrepanationBladeDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "defending player reveals cards from the top of his or her library until he or she reveals a land card. The creature gets +1/+0 until end of turn for each card revealed this way. That player puts the revealed cards into his or her graveyard";
    }

    public TrepanationBladeDiscardEffect(final TrepanationBladeDiscardEffect effect) {
        super(effect);
    }

    @Override
    public TrepanationBladeDiscardEffect copy() {
        return new TrepanationBladeDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature == null) {
                return false;
            }
            UUID defenderId = game.getCombat().getDefendingPlayer(creature.getId());
            Player player = game.getPlayer(defenderId);
            if (player == null) {
                return false;
            }

            CardsImpl cards = new CardsImpl();
            boolean landFound = false;
            while (player.getLibrary().size() > 0 && !landFound) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                    if (card.getCardType().contains(CardType.LAND)) {
                        landFound = true;
                    }
                }
            }

            if (creature != null && !cards.isEmpty()) {
                player.revealCards("Trepanation Blade", cards, game);
                game.getState().setValue(source.getSourceId().toString() + "_TrepanationBlade", cards.size());
                return true;
            }
        }

        game.getState().setValue(source.getSourceId().toString() + "_TrepanationBlade", null);
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode);    //To change body of overridden methods use File | Settings | File Templates.
    }
}

class TrepanationBladeBoostEffect extends ContinuousEffectImpl<TrepanationBladeBoostEffect> {

    public TrepanationBladeBoostEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
    }

    public TrepanationBladeBoostEffect(final TrepanationBladeBoostEffect effect) {
        super(effect);
    }

    @Override
    public TrepanationBladeBoostEffect copy() {
        return new TrepanationBladeBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amount = (Integer) game.getState().getValue(source.getSourceId().toString() + "_TrepanationBlade");

        Permanent equipment = game.getPermanent(source.getSourceId());
        if (amount != null && amount > 0 && equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null) {
                creature.addPower(amount);
                return true;
            }
        }

        return false;
    }
}
