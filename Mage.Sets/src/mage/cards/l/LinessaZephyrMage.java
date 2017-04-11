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
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public class LinessaZephyrMage extends CardImpl {

    private final UUID originalId;

    public LinessaZephyrMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}{U}{U}, {tap}: Return target creature with converted mana cost X to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl("{X}{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        originalId = ability.getOriginalId();
        this.addAbility(ability);

        // Grandeur - Discard another card named Linessa, Zephyr Mage: Target player returns a creature he or she controls to its owner's hand, then repeats this process for an artifact, an enchantment, and a land.
        ability = new GrandeurAbility(new LinessaZephyrMageEffect(), "Linessa, Zephyr Mage");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public LinessaZephyrMage(final LinessaZephyrMage card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            int xValue = ability.getManaCostsToPay().getX();
            ability.getTargets().clear();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with converted mana cost " + xValue);
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
            ability.getTargets().add(new TargetCreaturePermanent(filter));
        }
    }

    @Override
    public LinessaZephyrMage copy() {
        return new LinessaZephyrMage(this);
    }
}

class LinessaZephyrMageEffect extends OneShotEffect {

    LinessaZephyrMageEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Target player returns a creature he or she controls to its owner's hand, then repeats this process for an artifact, an enchantment, and a land";
    }

    LinessaZephyrMageEffect(final LinessaZephyrMageEffect effect) {
        super(effect);
    }

    @Override
    public LinessaZephyrMageEffect copy() {
        return new LinessaZephyrMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                // Target player returns a creature he or she controls to its owner's hand,
                Target target = new TargetControlledCreaturePermanent();
                target.setNotTarget(true);
                if (target.choose(Outcome.ReturnToHand, targetPlayer.getId(), source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        targetPlayer.moveCards(permanent, Zone.HAND, source, game);
                    }
                }

                // then repeats this process for an artifact,
                FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control");
                filter.add(new CardTypePredicate(CardType.ARTIFACT));
                target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (target.choose(Outcome.ReturnToHand, targetPlayer.getId(), source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        targetPlayer.moveCards(permanent, Zone.HAND, source, game);
                    }
                }

                // an enchantment,
                filter = new FilterControlledPermanent("enchantment you control");
                filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
                target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (target.choose(Outcome.ReturnToHand, targetPlayer.getId(), source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        targetPlayer.moveCards(permanent, Zone.HAND, source, game);
                    }
                }

                // and a land.
                filter = new FilterControlledPermanent("land you control");
                filter.add(new CardTypePredicate(CardType.LAND));
                target = new TargetControlledPermanent(filter);
                target.setNotTarget(true);
                if (target.choose(Outcome.ReturnToHand, targetPlayer.getId(), source.getSourceId(), game)) {
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        targetPlayer.moveCards(permanent, Zone.HAND, source, game);
                    }
                }

                return true;
            }
        }
        return false;
    }
}
