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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author spjspj
 */
public class PlanarGuide extends CardImpl {

    public PlanarGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{W}, Exile Planar Guide: Exile all creatures. At the beginning of the next end step, return those cards to the battlefield under their owners' control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PlanarGuideExileEffect(), new ManaCostsImpl("{3}{W}"));
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    public PlanarGuide(final PlanarGuide card) {
        super(card);
    }

    @Override
    public PlanarGuide copy() {
        return new PlanarGuide(this);
    }
}

class PlanarGuideExileEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("all creatures");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public PlanarGuideExileEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creatures. At the beginning of the next end step, return those cards to the battlefield under their owners' control.";
    }

    public PlanarGuideExileEffect(final PlanarGuideExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean creatureExiled = false;
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (creature != null) {
                if (creature.moveToExile(source.getSourceId(), "Planar Guide", source.getSourceId(), game)) {
                    creatureExiled = true;
                }
            }
        }
        if (creatureExiled) {
            // Create delayed triggered ability
            AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new PlanarGuideReturnFromExileEffect());
            game.addDelayedTriggeredAbility(delayedAbility, source);
            return true;
        }
        return true;
    }

    @Override
    public PlanarGuideExileEffect copy() {
        return new PlanarGuideExileEffect(this);
    }
}

class PlanarGuideReturnFromExileEffect extends OneShotEffect {

    public PlanarGuideReturnFromExileEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "At the beginning of the next end step, return those cards to the battlefield under their owners' control.";
    }

    public PlanarGuideReturnFromExileEffect(final PlanarGuideReturnFromExileEffect effect) {
        super(effect);
    }

    @Override
    public PlanarGuideReturnFromExileEffect copy() {
        return new PlanarGuideReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(source.getSourceId());
        if (exile != null) {
            exile = exile.copy();
            for (UUID cardId : exile) {
                Card card = game.getCard(cardId);
                card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
            }
            game.getExile().getExileZone(source.getSourceId()).clear();
            return true;
        }
        return false;
    }

}
