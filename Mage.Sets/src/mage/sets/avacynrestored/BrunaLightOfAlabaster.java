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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import java.util.UUID;
import mage.filter.predicate.other.CardCanTargetPermanentId;
import mage.filter.predicate.other.PermanentCanTargetPermanentId;

/**
 * @author noxx
 */
public class BrunaLightOfAlabaster extends CardImpl<BrunaLightOfAlabaster> {

    public BrunaLightOfAlabaster(UUID ownerId) {
        super(ownerId, 208, "Bruna, Light of Alabaster", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{U}");
        this.expansionSetCode = "AVR";
        this.supertype.add("Legendary");
        this.subtype.add("Angel");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Bruna, Light of Alabaster attacks or blocks, you may attach to it any number of Auras on the battlefield and you may put onto the battlefield attached to it any number of Aura cards that could enchant it from your graveyard and/or hand.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new BrunaLightOfAlabasterEffect(), true));
    }

    public BrunaLightOfAlabaster(final BrunaLightOfAlabaster card) {
        super(card);
    }

    @Override
    public BrunaLightOfAlabaster copy() {
        return new BrunaLightOfAlabaster(this);
    }
}

class BrunaLightOfAlabasterEffect extends OneShotEffect<BrunaLightOfAlabasterEffect> {

    private static final FilterPermanent filterAura = new FilterPermanent("Aura");
    private static final FilterCard filterAuraCard = new FilterCard("Aura card");

    static {
        filterAura.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAura.add(new SubtypePredicate("Aura"));
        filterAuraCard.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAuraCard.add(new SubtypePredicate("Aura"));
    }

    public BrunaLightOfAlabasterEffect() {
        super(Constants.Outcome.Benefit);
        this.staticText = "attach to it any number of Auras on the battlefield and you may put onto the battlefield attached to it any number of Aura cards that could enchant it from your graveyard and/or hand";
    }

    public BrunaLightOfAlabasterEffect(final BrunaLightOfAlabasterEffect effect) {
        super(effect);
    }

    @Override
    public BrunaLightOfAlabasterEffect copy() {
        return new BrunaLightOfAlabasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }

        while (player.chooseUse(Constants.Outcome.Benefit, "Attach an Aura from the battlefield?", game)) {
            filterAura.add(new PermanentCanTargetPermanentId(permanent.getId()));
            Target targetAura = new TargetPermanent(filterAura);
            if (player.choose(Constants.Outcome.Benefit, targetAura, source.getSourceId(), game)) {
                Permanent aura = game.getPermanent(targetAura.getFirstTarget());
                if (aura != null) {
                    Permanent attachedTo = game.getPermanent(aura.getAttachedTo());
                    if (attachedTo != null) {
                        attachedTo.removeAttachment(aura.getId(), game);
                    }
                    permanent.addAttachment(aura.getId(), game);
                }
            }
        }

        int count = player.getHand().count(filterAuraCard, game);
        while (count > 0 && player.chooseUse(Constants.Outcome.Benefit, "Attach an Aura from your hand?", game)) {
            filterAuraCard.add(new CardCanTargetPermanentId(permanent.getId()));
            TargetCard targetAura = new TargetCard(Constants.Zone.PICK, filterAuraCard);
            if (player.choose(Constants.Outcome.Benefit, player.getHand(), targetAura, game)) {
                Card aura = game.getCard(targetAura.getFirstTarget());
                if (aura != null) {
                    game.getState().setValue("attachTo:" + aura.getId(), permanent);
                    aura.putOntoBattlefield(game, Constants.Zone.HAND, source.getId(), player.getId());
                    permanent.addAttachment(aura.getId(), game);
                }
            }
            count = player.getHand().count(filterAuraCard, game);
        }

        count = player.getGraveyard().count(filterAuraCard, game);
        while (count > 0 && player.chooseUse(Constants.Outcome.Benefit, "Attach an Aura from your graveyard?", game)) {
            filterAuraCard.add(new CardCanTargetPermanentId(permanent.getId()));
            TargetCard targetAura = new TargetCard(Constants.Zone.PICK, filterAuraCard);
            if (player.choose(Constants.Outcome.Benefit, player.getGraveyard(), targetAura, game)) {
                Card aura = game.getCard(targetAura.getFirstTarget());
                if (aura != null) {
                    game.getState().setValue("attachTo:" + aura.getId(), permanent);
                    aura.putOntoBattlefield(game, Constants.Zone.GRAVEYARD, source.getId(), player.getId());
                    permanent.addAttachment(aura.getId(), game);
                }
            }
            count = player.getGraveyard().count(filterAuraCard, game);
        }

        return true;
    }
}