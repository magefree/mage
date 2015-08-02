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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class ShorecrasherElemental extends CardImpl {

    public ShorecrasherElemental(UUID ownerId) {
        super(ownerId, 73, "Shorecrasher Elemental", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{U}{U}{U}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Elemental");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {U}: Exile Shorecrasher Elemental, then return it to the battlefield face down under its owner's control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShorecrasherElementalEffect(), new ManaCostsImpl("{U}")));

        // {1}: Shorecrasher Elemental gets +1/-1 or -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShorecrasherElementalBoostEffect(), new ManaCostsImpl("{1}")));

        // Megamorph {4}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{4}{U}"), true));

    }

    public ShorecrasherElemental(final ShorecrasherElemental card) {
        super(card);
    }

    @Override
    public ShorecrasherElemental copy() {
        return new ShorecrasherElemental(this);
    }
}

class ShorecrasherElementalEffect extends OneShotEffect {

    public ShorecrasherElementalEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield face down under its owner's control";
    }

    public ShorecrasherElementalEffect(final ShorecrasherElementalEffect effect) {
        super(effect);
    }

    @Override
    public ShorecrasherElementalEffect copy() {
        return new ShorecrasherElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent shorecrasherElemental = game.getPermanent(source.getSourceId());
        MageObject sourceObject = source.getSourceObject(game);
        if (shorecrasherElemental != null &&
                sourceObject != null &&
                new MageObjectReference(sourceObject, game).refersTo(shorecrasherElemental, game)) {
            if (shorecrasherElemental.moveToExile(source.getSourceId(), sourceObject.getName(), source.getSourceId(), game)) {
                Card card = game.getExile().getCard(source.getSourceId(), game);
                if (card != null) {
                    game.addEffect(new BecomesFaceDownCreatureEffect(Duration.Custom, FaceDownType.MEGAMORPHED), source);
                    return card.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), card.getOwnerId(), false, true);

                }
            }
        }
        return false;
    }
}

class ShorecrasherElementalBoostEffect extends OneShotEffect {

    private static String CHOICE_1 = "+1/-1";
    private static String CHOICE_2 = "-1/+1";

    public ShorecrasherElementalBoostEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +1/-1 or -1/+1 until end of turn";
    }

    public ShorecrasherElementalBoostEffect(final ShorecrasherElementalBoostEffect effect) {
        super(effect);
    }

    @Override
    public ShorecrasherElementalBoostEffect copy() {
        return new ShorecrasherElementalBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Select how to boost");
            choice.getChoices().add(CHOICE_1);
            choice.getChoices().add(CHOICE_2);
            while (!choice.isChosen()) {
                if (!controller.canRespond()) {
                    return false;
                }
                controller.choose(outcome, choice, game);
            }
            if (choice.getChoice().equals(CHOICE_1)) {
                game.addEffect(new BoostSourceEffect(+1, -1, Duration.EndOfTurn), source);
            } else {
                game.addEffect(new BoostSourceEffect(-1, +1, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}
