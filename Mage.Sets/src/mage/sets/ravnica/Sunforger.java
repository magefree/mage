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
package mage.sets.ravnica;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.Filter.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public class Sunforger extends CardImpl {

    public Sunforger(UUID ownerId) {
        super(ownerId, 272, "Sunforger", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Equipment");

        // Equipped creature gets +4/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(4, 0, Duration.WhileOnBattlefield)));

        // {R}{W}, Unattach Sunforger: Search your library for a red or white instant card with converted mana cost 4 or less and cast that card without paying its mana cost. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SunforgerEffect(), new ManaCostsImpl("{R}{W}"));
        ability.addCost(new UnattachSourceCost());
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

    private static final FilterCard filter = new FilterCard("red or white instant card with converted mana cost 4 or less");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE)));
        filter.add(new CardTypePredicate(CardType.INSTANT));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.LessThan, 5));
    }

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
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            if (you.getLibrary().size() > 0) {
                TargetCardInLibrary target = new TargetCardInLibrary(filter);
                if (you.searchLibrary(target, game, you.getId())) {
                    UUID targetId = target.getFirstTarget();
                    Card card = you.getLibrary().remove(targetId, game);
                    if (card != null) {
                        you.cast(card.getSpellAbility(), game, true);
                    }
                }
            }
            you.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}

class UnattachSourceCost extends CostImpl {

    public UnattachSourceCost() {
        this.text = "Unattach Sunforger";
    }

    public UnattachSourceCost(UnattachSourceCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent attachment = game.getPermanent(sourceId);
        Permanent permanent = game.getPermanent(attachment.getAttachedTo());
        if (permanent != null) {
            paid = permanent.removeAttachment(attachment.getId(), game);
            if (paid) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.UNATTACHED, sourceId, sourceId, controllerId));
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent attachment = game.getPermanent(sourceId);
        if (attachment != null) {
            return attachment.getAttachedTo() != null;
        }
        return false;
    }

    @Override
    public UnattachSourceCost copy() {
        return new UnattachSourceCost(this);
    }
}
