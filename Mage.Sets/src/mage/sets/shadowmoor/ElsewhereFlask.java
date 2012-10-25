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
package mage.sets.shadowmoor;

import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.choices.ChoiceImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ElsewhereFlask extends CardImpl<ElsewhereFlask> {

    public ElsewhereFlask(UUID ownerId) {
        super(ownerId, 250, "Elsewhere Flask", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "SHM";

        // When Elsewhere Flask enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardControllerEffect(1)));

        // Sacrifice Elsewhere Flask: Choose a basic land type. Each land you control becomes that type until end of turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ElsewhereFlaskEffect(), new SacrificeSourceCost()));
    }

    public ElsewhereFlask(final ElsewhereFlask card) {
        super(card);
    }

    @Override
    public ElsewhereFlask copy() {
        return new ElsewhereFlask(this);
    }
}

class ElsewhereFlaskEffect extends OneShotEffect<ElsewhereFlaskEffect> {

    public ElsewhereFlaskEffect() {
        super(Constants.Outcome.Neutral);
        this.staticText = "Choose a basic land type.  Each land you control becomes that type until end of turn";
    }

    public ElsewhereFlaskEffect(final ElsewhereFlaskEffect effect) {
        super(effect);
    }

    @Override
    public ElsewhereFlaskEffect copy() {
        return new ElsewhereFlaskEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            ChoiceImpl choices = new ChoiceImpl(true);
            Set choicesSet = choices.getChoices();
            choicesSet.add("Forest");
            choicesSet.add("Plains");
            choicesSet.add("Mountain");
            choicesSet.add("Island");
            choicesSet.add("Swamp");
            if (player.choose(Constants.Outcome.Neutral, choices, game)) {
                game.getState().setValue(source.getSourceId().toString() + "_ElsewhereFlask", choices.getChoice());
            }
            game.addEffect(new ElsewhereFlaskContinuousEffect(), source);
            return true;
        }
        return false;
    }
}

class ElsewhereFlaskContinuousEffect extends ContinuousEffectImpl<ElsewhereFlaskContinuousEffect> {

    public ElsewhereFlaskContinuousEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Outcome.Neutral);
    }

    public ElsewhereFlaskContinuousEffect(final ElsewhereFlaskContinuousEffect effect) {
        super(effect);
    }

    @Override
    public ElsewhereFlaskContinuousEffect copy() {
        return new ElsewhereFlaskContinuousEffect(this);
    }

    @Override
    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        FilterPermanent filter = new FilterPermanent();
        filter.add(new CardTypePredicate(CardType.LAND));
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
        String choice = (String) game.getState().getValue(source.getSourceId().toString() + "_ElsewhereFlask");
        if (choice != null) {
            for (Permanent land : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (land != null) {
                    switch (layer) {
                        case TypeChangingEffects_4:
                            if (sublayer == Constants.SubLayer.NA) {
                                land.getSubtype().clear();
                                land.getSubtype().add(choice);
                            }
                            break;
                        case AbilityAddingRemovingEffects_6:
                            if (sublayer == Constants.SubLayer.NA) {
                                land.getAbilities().clear();
                                if (choice.equals("Forest")) {
                                    land.addAbility(new GreenManaAbility(), id, game);
                                }
                                if (choice.equals("Plains")) {
                                    land.addAbility(new WhiteManaAbility(), id, game);
                                }
                                if (choice.equals("Mountain")) {
                                    land.addAbility(new RedManaAbility(), id, game);
                                }
                                if (choice.equals("Island")) {
                                    land.addAbility(new BlueManaAbility(), id, game);
                                }
                                if (choice.equals("Swamp")) {
                                    land.addAbility(new BlackManaAbility(), id, game);
                                }
                            }
                            break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Constants.Layer layer) {
        return layer == Constants.Layer.AbilityAddingRemovingEffects_6 || layer == Constants.Layer.TypeChangingEffects_4;
    }
}
