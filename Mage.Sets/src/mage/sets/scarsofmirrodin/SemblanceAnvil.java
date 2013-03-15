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
package mage.sets.scarsofmirrodin;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public class SemblanceAnvil extends CardImpl<SemblanceAnvil> {

    public SemblanceAnvil(UUID ownerId) {
        super(ownerId, 201, "Semblance Anvil", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "SOM";

        // Imprint - When Semblance Anvil enters the battlefield, you may exile a nonland card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SemblanceAnvilEffect(), true));

        // Spells you cast that share a card type with the exiled card cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new SemblanceAnvilCostReductionEffect()));
    }

    public SemblanceAnvil(final SemblanceAnvil card) {
        super(card);
    }

    @Override
    public SemblanceAnvil copy() {
        return new SemblanceAnvil(this);
    }
}

class SemblanceAnvilEffect extends OneShotEffect<SemblanceAnvilEffect> {

    private static FilterCard filter = new FilterNonlandCard();

    public SemblanceAnvilEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "exile a nonland card from your hand";
    }

    public SemblanceAnvilEffect(SemblanceAnvilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player.getHand().size() > 0) {
            TargetCard target = new TargetCard(Constants.Zone.HAND, filter);
            player.choose(Constants.Outcome.Benefit, player.getHand(), target, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                card.moveToExile(getId(), "Semblance Anvil (Imprint)", source.getSourceId(), game);
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    permanent.imprint(card.getId(), game);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public SemblanceAnvilEffect copy() {
        return new SemblanceAnvilEffect(this);
    }

}

class SemblanceAnvilCostReductionEffect extends CostModificationEffectImpl<SemblanceAnvilCostReductionEffect> {

    private static final String effectText = "Spells you cast that share a card type with the exiled card cost {2} less to cast";

    SemblanceAnvilCostReductionEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = effectText;
    }

    SemblanceAnvilCostReductionEffect(SemblanceAnvilCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility || abilityToModify instanceof RetraceAbility) {
            Card sourceCard = game.getCard(abilityToModify.getSourceId());
            if (sourceCard != null && sourceCard.getOwnerId().equals(source.getControllerId())) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    List<UUID> imprinted = permanent.getImprinted();
                    if (imprinted.size() > 0) {
                        Card imprintedCard = game.getCard(imprinted.get(0));
                        if (imprintedCard != null && CardUtil.shareTypes(imprintedCard, sourceCard)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public SemblanceAnvilCostReductionEffect copy() {
        return new SemblanceAnvilCostReductionEffect(this);
    }

}
