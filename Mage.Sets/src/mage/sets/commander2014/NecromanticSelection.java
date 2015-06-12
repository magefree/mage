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
package mage.sets.commander2014;

import java.util.ArrayList;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.ColorChangingEffects_5;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class NecromanticSelection extends CardImpl {

    public NecromanticSelection(UUID ownerId) {
        super(ownerId, 26, "Necromantic Selection", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{B}{B}{B}");
        this.expansionSetCode = "C14";


        // Destroy all creatures, then return a creature card put into a graveyard this way to the battlefield under your control. It's a black Zombie in addition to its other colors and types. Exile Necromantic Selection.
        this.getSpellAbility().addEffect(new NecromanticSelectionEffect());
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

    }

    public NecromanticSelection(final NecromanticSelection card) {
        super(card);
    }

    @Override
    public NecromanticSelection copy() {
        return new NecromanticSelection(this);
    }
}

class NecromanticSelectionEffect extends OneShotEffect {

    public NecromanticSelectionEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures, then return a creature card put into a graveyard this way to the battlefield under your control. It's a black Zombie in addition to its other colors and types";
    }

    public NecromanticSelectionEffect(final NecromanticSelectionEffect effect) {
        super(effect);
    }

    @Override
    public NecromanticSelectionEffect copy() {
        return new NecromanticSelectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl();
            for(Permanent permanent: game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), controller.getId(), source.getSourceId(), game)) {
                permanent.destroy(source.getSourceId(), game, false);
                if (game.getState().getZone(permanent.getId()).equals(Zone.GRAVEYARD)) {
                    cards.add(permanent);
                }
            }
            FilterCard filter = new FilterCreatureCard("creature card put into a graveyard with " + sourceObject.getLogName());
            ArrayList<Predicate<MageObject>> cardIdPredicates = new ArrayList<>();
            for(UUID cardId: cards) {
                cardIdPredicates.add(new CardIdPredicate(cardId));
            }
            filter.add(Predicates.or(cardIdPredicates));
            Target target = new TargetCardInGraveyard(filter);
            if (controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId());
                    ContinuousEffect effect = new NecromanticSelectionContinuousEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class NecromanticSelectionContinuousEffect extends ContinuousEffectImpl {

    public NecromanticSelectionContinuousEffect() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "It's a black Zombie in addition to its other colors and types";
    }

    public NecromanticSelectionContinuousEffect(final NecromanticSelectionContinuousEffect effect) {
        super(effect);
    }

    @Override
    public NecromanticSelectionContinuousEffect copy() {
        return new NecromanticSelectionContinuousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (!creature.getSubtype().contains("Zombie")) {
                            creature.getSubtype().add("Zombie");
                        }
                    }
                    break;
                case ColorChangingEffects_5:
                    if (sublayer == SubLayer.NA) {
                        creature.getColor(game).setBlack(true);
                    }
                    break;
            }
            return true;
        } else {
            this.used = true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
