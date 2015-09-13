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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
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
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public class SoulFoundry extends CardImpl {

    public SoulFoundry(UUID ownerId) {
        super(ownerId, 246, "Soul Foundry", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "MRD";

        // Imprint - When Soul Foundry enters the battlefield, you may exile a creature card from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SoulFoundryImprintEffect(), true, "<i>Imprint - </i>"));

        // {X}, {T}: Put a token that's a copy of the exiled card onto the battlefield. X is the converted mana cost of that card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoulFoundryEffect(), new ManaCostsImpl("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public SoulFoundry(final SoulFoundry card) {
        super(card);
    }

    @java.lang.Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SimpleActivatedAbility) {
            Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
            if (sourcePermanent != null) {
                if (sourcePermanent.getImprinted().size() > 0) {
                    Card imprinted = game.getCard(sourcePermanent.getImprinted().get(0));
                    if (imprinted != null) {
                        ability.getManaCostsToPay().clear();
                        ability.getManaCostsToPay().add(0, new GenericManaCost(imprinted.getManaCost().convertedManaCost()));
                    }
                }
            }

            // no {X} anymore as we already have imprinted the card with defined manacost
            for (ManaCost cost : ability.getManaCostsToPay()) {
                if (cost instanceof VariableCost) {
                    cost.setPaid();
                }
            }
        }
    }

    @java.lang.Override
    public SoulFoundry copy() {
        return new SoulFoundry(this);
    }
}

class SoulFoundryImprintEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature card from your hand");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public SoulFoundryImprintEffect() {
        super(Outcome.Neutral);
        staticText = "you may exile a creature card from your hand";
    }

    public SoulFoundryImprintEffect(SoulFoundryImprintEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null) {
            if (controller.getHand().size() > 0) {
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (target.canChoose(source.getSourceId(), source.getControllerId(), game)
                        && controller.choose(Outcome.Benefit, controller.getHand(), target, game)) {
                    Card card = controller.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, source.getSourceId(), sourcePermanent.getIdName() + " (Imprint)", source.getSourceId(), game, Zone.HAND, true);
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.imprint(card.getId(), game);
                            permanent.addInfo("imprint", CardUtil.addToolTipMarkTags("[Imprinted card - " + card.getLogName() + "]"), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @java.lang.Override
    public SoulFoundryImprintEffect copy() {
        return new SoulFoundryImprintEffect(this);
    }
}

class SoulFoundryEffect extends OneShotEffect {

    public SoulFoundryEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a token that's a copy of the exiled card onto the battlefield. X is the converted mana cost of that card";
    }

    public SoulFoundryEffect(final SoulFoundryEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent soulFoundry = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (soulFoundry != null
                    && soulFoundry.getImprinted() != null
                    && !soulFoundry.getImprinted().isEmpty()) {
                Card imprinted = game.getCard(soulFoundry.getImprinted().get(0));
                if (imprinted != null
                        && game.getState().getZone(imprinted.getId()).equals(Zone.EXILED)) {
                    PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(imprinted.getId(), imprinted.getZoneChangeCounter(game)));
                    return effect.apply(game, source);
                }
            }
        }
        return false;
    }

    @java.lang.Override
    public SoulFoundryEffect copy() {
        return new SoulFoundryEffect(this);
    }
}
