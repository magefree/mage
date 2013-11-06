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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 *
 */
public class Evershrike extends CardImpl<Evershrike> {

    public Evershrike(UUID ownerId) {
        super(ownerId, 88, "Evershrike", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");
        this.subtype.add("Spirit");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Evershrike gets +2/+2 for each Aura attached to it.
        AuraAttachedCount amount = new AuraAttachedCount(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield)));

        // {X}{WB}{WB}: Return Evershrike from your graveyard to the battlefield. You may put an Aura card with converted mana cost X or less from your hand onto the battlefield attached to it. If you don't, exile Evershrike.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new EvershrikeEffect(), new ManaCostsImpl("{X}{W/B}{W/B}")));

    }

    public Evershrike(final Evershrike card) {
        super(card);
    }

    @Override
    public Evershrike copy() {
        return new Evershrike(this);
    }
}

class EvershrikeEffect extends OneShotEffect<EvershrikeEffect> {

    public EvershrikeEffect() {
        super(Outcome.Benefit);
        staticText = "Return {this} from your graveyard to the battlefield. You may put an Aura card with converted mana cost X or less from your hand onto the battlefield attached to it. If you don't, exile {this}";
    }

    public EvershrikeEffect(final EvershrikeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean exiled = true;
        Card evershrikeCard = game.getCard(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        int xAmount = source.getManaCostsToPay().getX() + 1;
        if (evershrikeCard != null) {
            if (evershrikeCard.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false)) {
                Permanent evershrikePermanent = game.getPermanent(source.getSourceId());
                if (evershrikePermanent == null) {
                    return false;
                }
                FilterCard filterAuraCard = new FilterCard("Aura card with converted mana cost X or less from your hand");
                filterAuraCard.add(new CardTypePredicate(CardType.ENCHANTMENT));
                filterAuraCard.add(new SubtypePredicate("Aura"));
                filterAuraCard.add(new AuraCardCanAttachToPermanentId(evershrikePermanent.getId()));
                filterAuraCard.add(new ConvertedManaCostPredicate(ComparisonType.LessThan, xAmount));
                int count = you.getHand().count(filterAuraCard, game);
                while (count > 0 && you.chooseUse(Outcome.Benefit, "Do you wish to put an Aura card from your hand onto Evershrike", game)) {
                    TargetCard targetAura = new TargetCard(Zone.PICK, filterAuraCard);
                    if (you.choose(Outcome.Benefit, you.getHand(), targetAura, game)) {
                        Card aura = game.getCard(targetAura.getFirstTarget());
                        if (aura != null) {
                            game.getState().setValue("attachTo:" + aura.getId(), evershrikePermanent);
                            aura.putOntoBattlefield(game, Zone.HAND, source.getId(), you.getId());
                            evershrikePermanent.addAttachment(aura.getId(), game);
                            exiled = false;
                            count = you.getHand().count(filterAuraCard, game);
                        }
                    }


                }
                if (exiled) {
                    return evershrikePermanent.moveToExile(source.getSourceId(), "Evershrike Exile", source.getId(), game);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public EvershrikeEffect copy() {
        return new EvershrikeEffect(this);
    }
}