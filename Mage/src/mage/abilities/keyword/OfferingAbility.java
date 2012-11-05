/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.keyword;

import java.util.UUID;
import mage.Constants;
import mage.Constants.AsThoughEffectType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.StaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.cards.Card;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * 702.46. Offering #
 *   702.46a Offering is a static ability of a card that functions in any zone from which
 *   the card can be cast. "[Subtype] offering" means "You may cast this card any time you
 *   could cast an instant by sacrificing a [subtype] permanent. If you do, the total cost
 *   to cast this card is reduced by the sacrificed permanent's mana cost." #
 *
 *   702.46b The permanent is sacrificed at the same time the spell is announced (see rule 601.2a).
 *   The total cost of the spell is reduced by the sacrificed permanent's mana cost (see rule 601.2e). #
 *
 *   702.46c Generic mana in the sacrificed permanent's mana cost reduces generic mana
 *   in the total cost to cast the card with offering. Colored mana in the sacrificed
 *   permanent's mana cost reduces mana of the same color in the total cost to cast the
 *   card with offering. Colored mana in the sacrificed permanent's mana cost that doesn't
 *   match colored mana in the colored mana cost of the card with offering, or is in excess
 *   of the card's colored mana cost, reduces that much generic mana in the total cost. #
 *
 * @param subtype name of the subtype that can be offered
 *
 * @author LevelX2
 */
public class OfferingAbility extends StaticAbility<OfferingAbility> {

    private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    public OfferingAbility(String subtype) {
        super(Zone.ALL, null);
        filter.add(new SubtypePredicate(subtype));
        filter.setMessage(subtype);
        this.addEffect(new OfferingAsThoughEffect());
    }

    public OfferingAbility(OfferingAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public OfferingAbility copy() {
        return new OfferingAbility(this);
    }

    public FilterControlledCreaturePermanent getFilter() {
        return filter;
    }

    @Override
    public String getRule(boolean all) {
        String subtype = filter.getMessage();
        return subtype + " offering <i>(You may cast this card any time you could cast an instant by sacrificing a " + subtype + " and paying the difference in mana costs between this and the sacrificed " + subtype + ". Mana cost includes color.)</i>";
    }
}

class OfferingAsThoughEffect extends AsThoughEffectImpl<OfferingAsThoughEffect> {

    public OfferingAsThoughEffect() {
        super(AsThoughEffectType.CAST, Duration.EndOfGame, Outcome.Benefit);
    }

    public OfferingAsThoughEffect(final OfferingAsThoughEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OfferingAsThoughEffect copy() {
        return new OfferingAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (sourceId.equals(source.getSourceId())) {
            Card card = game.getCard(sourceId);
            if (!card.getOwnerId().equals(source.getControllerId())) {
                return false;
            }
            // because can activate is always called twice, result from first call will be used
            Object object = game.getState().getValue("offering_" + card.getId());
            if (object != null && object.equals(true)) {
                Object alreadyConfirmed = game.getState().getValue("offering_ok_" + card.getId());
                game.getState().setValue("offering_" + card.getId(), null);
                game.getState().setValue("offering_ok_" + card.getId(), null);
                if (alreadyConfirmed != null) {
                    return true;
                }
                return false;
            } else {
                // first call -> remove previous Ids
                game.getState().setValue("offering_Id_" + card.getId(), null);
            }

            if (game.getBattlefield().count(((OfferingAbility) source).getFilter(), source.getControllerId(), game) > 0) {

                FilterControlledCreaturePermanent filter = ((OfferingAbility) source).getFilter();
                Card spellToCast = game.getCard(source.getSourceId());
                Player player = game.getPlayer(source.getControllerId());
                if (player != null && player.chooseUse(Outcome.Benefit, "Offer a " + filter.getMessage() + " to cast " + spellToCast.getName() + "?", game)) {
                    Target target = new TargetControlledCreaturePermanent(1,1,filter,true);
                    player.chooseTarget(Outcome.Sacrifice, target, source, game);
                    if (!target.isChosen()) {
                        return false;
                    }
                    game.getState().setValue("offering_" + card.getId(), true);
                    Permanent offer = game.getPermanent(target.getFirstTarget());
                    if (offer != null) {
                        UUID activationId = UUID.randomUUID();
                        OfferingCostReductionEffect effect = new OfferingCostReductionEffect(spellToCast.getSpellAbility().getId(), offer.getSpellAbility().getManaCosts(), activationId);
                        game.addEffect(effect, source);
                        offer.sacrifice(source.getSourceId(), game);
                        game.getState().setValue("offering_ok_" + card.getId(), true);
                        game.getState().setValue("offering_Id_" + card.getId(), activationId);
                        return true;
                       
                    }
                } else {
                    if (game.canPlaySorcery(source.getControllerId())) {
                         game.getState().setValue("offering_" + card.getId(), true);
                    }
                }
            }
        }
        return false;
    }
}


class OfferingCostReductionEffect extends CostModificationEffectImpl<OfferingCostReductionEffect> {

    private UUID spellAbilityId;
    private UUID activationId;
    private ManaCosts<ManaCost> manaCostsToReduce;

    OfferingCostReductionEffect (UUID spellAbilityId, ManaCosts<ManaCost> manaCostsToReduce, UUID activationId) {
        super(Constants.Duration.OneUse, Constants.Outcome.Benefit);
        this.spellAbilityId = spellAbilityId;
        this.manaCostsToReduce = manaCostsToReduce;
        this.activationId = activationId;
        staticText = "mana costs reduction from offering";
    }

    OfferingCostReductionEffect(OfferingCostReductionEffect effect) {
        super(effect);
        this.spellAbilityId = effect.spellAbilityId;
        this.manaCostsToReduce = effect.manaCostsToReduce;
        this.activationId = effect.activationId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.adjustCost((SpellAbility) abilityToModify, manaCostsToReduce);
        this.used = true;
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getId().equals(spellAbilityId) && abilityToModify instanceof SpellAbility) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                   Object object = game.getState().getValue("offering_Id_" + card.getId());
                   if (object != null && ((UUID) object).equals(this.activationId)) {
                       return true;
                   }
            }
            // no or other id, this effect is no longer valid
            this.used = true;
        }
        return false;
    }

    @Override
    public OfferingCostReductionEffect copy() {
        return new OfferingCostReductionEffect(this);
    }
}