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
package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class Sunforger extends CardImpl {

    public Sunforger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add("Equipment");

        // Equipped creature gets +4/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(4, 0, Duration.WhileOnBattlefield)));

        // {R}{W}, Unattach Sunforger: Search your library for a red or white instant card with converted mana cost 4 or less and cast that card without paying its mana cost. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SunforgerEffect(), new ManaCostsImpl("{R}{W}"));
        ability.addCost(new SunforgerUnattachCost(this.getName()));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));

    }

    public Sunforger(final Sunforger card) {
        super(card);
    }

    @Override
    public Sunforger copy() {
        return new Sunforger(this);
    }
}

class SunforgerEffect extends OneShotEffect {

    public SunforgerEffect() {
        super(Outcome.PlayForFree);
        staticText = "Search your library for a red or white instant card with converted mana cost 4 or less and cast that card without paying its mana cost. Then shuffle your library";
    }

    public SunforgerEffect(final SunforgerEffect effect) {
        super(effect);
    }

    @Override
    public SunforgerEffect copy() {
        return new SunforgerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getLibrary().hasCards()) {
                /**
                 * 10/1/2005 Any card you find must be legally castable (for
                 * example, you have to be able to choose a legal target for
                 * it). If you can’t find a castable card (or choose not to),
                 * nothing happens and you shuffle your library.
                 */

                FilterCard filter = new FilterCard("red or white instant card with converted mana cost 4 or less");
                TargetCardInLibrary target = new TargetCardInLibrary(filter);
                filter.add(Predicates.or(
                        new ColorPredicate(ObjectColor.RED),
                        new ColorPredicate(ObjectColor.WHITE)));
                filter.add(new CardTypePredicate(CardType.INSTANT));
                filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 5));
                filter.add(new CardCanBeCastPredicate(source.getControllerId()));
                if (controller.searchLibrary(target, game, controller.getId())) {
                    UUID targetId = target.getFirstTarget();
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        controller.cast(card.getSpellAbility(), game, true);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}

class SunforgerUnattachCost extends CostImpl {

    public SunforgerUnattachCost(String name) {
        this.text = "Unattach " + name;
    }

    public SunforgerUnattachCost(final SunforgerUnattachCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent attachment = game.getPermanent(sourceId);
        if (attachment != null && attachment.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
            if (attachedTo != null) {
                paid = attachedTo.removeAttachment(attachment.getId(), game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent attachment = game.getPermanent(sourceId);
        if (attachment != null && attachment.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
            if (attachedTo != null) {
                return true;
            }

        }
        return false;
    }

    @Override
    public SunforgerUnattachCost copy() {
        return new SunforgerUnattachCost(this);
    }
}

class CardCanBeCastPredicate implements Predicate<Card> {

    private final UUID controllerId;

    public CardCanBeCastPredicate(UUID controllerId) {
        this.controllerId = controllerId;
    }

    @Override
    public boolean apply(Card input, Game game) {
        SpellAbility ability = input.getSpellAbility().copy();
        ability.setControllerId(controllerId);
        input.adjustTargets(ability, game);
        return ability.canChooseTarget(game);
    }

    @Override
    public String toString() {
        return "CardCanBeCastPredicate";
    }
}
