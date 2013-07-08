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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class EliteArcanist extends CardImpl<EliteArcanist> {

    public EliteArcanist(UUID ownerId) {
        super(ownerId, 54, "Elite Arcanist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "M14";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Elite Arcanist enters the battlefield, you may exile an instant card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EliteArcanistImprintEffect(), true));

        // {X}, {T}: Copy the exiled card. You may cast the copy without paying its mana cost. X is the converted mana cost of the exiled card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EliteArcanistCopyEffect(), new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public EliteArcanist(final EliteArcanist card) {
        super(card);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if(ability instanceof SimpleActivatedAbility){
            Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
            if (sourcePermanent != null && sourcePermanent.getImprinted() != null && !sourcePermanent.getImprinted().isEmpty()) {
                Card imprintedInstant = game.getCard(sourcePermanent.getImprinted().get(0));
                if (imprintedInstant != null) {
                    int cmc = imprintedInstant.getManaCost().convertedManaCost();
                    if (cmc > 0) {
                        ability.getManaCostsToPay().clear();
                        ability.getManaCostsToPay().add(new GenericManaCost(cmc));
                    }
                }
            }
        }
    }

    @Override
    public EliteArcanist copy() {
        return new EliteArcanist(this);
    }
}

class EliteArcanistImprintEffect extends OneShotEffect<EliteArcanistImprintEffect> {

    private static final FilterCard filter = new FilterCard("instant card from your hand");
    static  {
        filter.add(new CardTypePredicate(CardType.INSTANT));
    }

    public EliteArcanistImprintEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile an instant card from your hand";
    }

    public EliteArcanistImprintEffect(EliteArcanistImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player.getHand().size() > 0) {
            TargetCard target = new TargetCard(Zone.HAND, filter);
            target.setRequired(true);
            if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                    && player.choose(Outcome.Benefit, player.getHand(), target, game)) {
                Card card = player.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    card.moveToExile(source.getSourceId(), "Elite Arcanist", source.getSourceId(), game);
                    Permanent permanent = game.getPermanent(source.getSourceId());
                    if (permanent != null) {
                        permanent.imprint(card.getId(), game);
                        permanent.addInfo("imprint", new StringBuilder("[Exiled card - ").append(card.getName()).append("]").toString());
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public EliteArcanistImprintEffect copy() {
        return new EliteArcanistImprintEffect(this);
    }

}

class EliteArcanistCopyEffect extends OneShotEffect<EliteArcanistCopyEffect> {

    public EliteArcanistCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "Copy the exiled card. You may cast the copy without paying its mana cost. X is the converted mana cost of the exiled card";
    }

    public EliteArcanistCopyEffect(final EliteArcanistCopyEffect effect) {
        super(effect);
    }

    @Override
    public EliteArcanistCopyEffect copy() {
        return new EliteArcanistCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null && sourcePermanent.getImprinted() != null && !sourcePermanent.getImprinted().isEmpty()) {
            Card imprintedInstant = game.getCard(sourcePermanent.getImprinted().get(0));
            if (imprintedInstant != null && game.getState().getZone(imprintedInstant.getId()).equals(Zone.EXILED)) {
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    Card copiedCard = game.copyCard(imprintedInstant, source, source.getControllerId());
                    if (copiedCard != null) {
                        game.getExile().add(source.getSourceId(), "",copiedCard);
                        game.getState().setZone(copiedCard.getId(), Zone.EXILED);
                        if (controller.chooseUse(outcome, "Cast the copied card without paying mana cost?", game)) {
                            return controller.cast(copiedCard.getSpellAbility(), game, true);
                        }
                    }
                }
            }
        }
        return false;
    }
}
