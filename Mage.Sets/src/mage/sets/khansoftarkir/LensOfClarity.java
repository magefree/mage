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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class LensOfClarity extends CardImpl {

    public LensOfClarity(UUID ownerId) {
        super(ownerId, 223, "Lens of Clarity", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "KTK";

        // You may look at the top card of your library and at face-down creatures you don't control.
        // TODO: this efffects should be a static abilities and not use activated abilities (because it could than be restriced)
        this.addAbility(new LensOfClarityLookLibraryAbility());
        this.addAbility(new LensOfClarityLookFaceDownAbility());
    }

    public LensOfClarity(final LensOfClarity card) {
        super(card);
    }

    @Override
    public LensOfClarity copy() {
        return new LensOfClarity(this);
    }
}

class LensOfClarityLookLibraryAbility extends ActivatedAbilityImpl {

    public LensOfClarityLookLibraryAbility() {
        super(Zone.BATTLEFIELD, new LensOfClarityLookLibraryEffect(), new GenericManaCost(0));
        this.usesStack = false;
    }

    public LensOfClarityLookLibraryAbility(LensOfClarityLookLibraryAbility ability) {
        super(ability);
    }

    @Override
    public LensOfClarityLookLibraryAbility copy() {
        return new LensOfClarityLookLibraryAbility(this);
    }

}

class LensOfClarityLookLibraryEffect extends OneShotEffect {

    public LensOfClarityLookLibraryEffect() {
        super(Outcome.Neutral);
        this.staticText = "You may look at the top card of your library";
    }

    public LensOfClarityLookLibraryEffect(final LensOfClarityLookLibraryEffect effect) {
        super(effect);
    }

    @Override
    public LensOfClarityLookLibraryEffect copy() {
        return new LensOfClarityLookLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller == null || mageObject == null) {
            return false;
        }

        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl();
            cards.add(card);
            controller.lookAtCards("top card of library - " + controller.getName(), cards, game);
            game.informPlayers(controller.getLogName() + " looks at the top card of his or her library");
        } else {
            return false;
        }

        return true;
    }
}

class LensOfClarityLookFaceDownAbility extends ActivatedAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face down creature you don't control");

    static {
        filter.add(new FaceDownPredicate());
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public LensOfClarityLookFaceDownAbility() {
        super(Zone.BATTLEFIELD, new LensOfClarityLookFaceDownEffect(), new GenericManaCost(0));
        this.usesStack = false;
        this.addTarget(new TargetCreaturePermanent(filter));
    }

    public LensOfClarityLookFaceDownAbility(LensOfClarityLookFaceDownAbility ability) {
        super(ability);
    }

    @Override
    public LensOfClarityLookFaceDownAbility copy() {
        return new LensOfClarityLookFaceDownAbility(this);
    }

}

class LensOfClarityLookFaceDownEffect extends OneShotEffect {

    public LensOfClarityLookFaceDownEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may look at face-down creatures you don't control";
    }

    public LensOfClarityLookFaceDownEffect(final LensOfClarityLookFaceDownEffect effect) {
        super(effect);
    }

    @Override
    public LensOfClarityLookFaceDownEffect copy() {
        return new LensOfClarityLookFaceDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller=  game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller == null || mageObject == null) {
            return false;
        }
        Permanent faceDownCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (faceDownCreature != null) {
            Permanent copyFaceDown = faceDownCreature.copy();
            copyFaceDown.setFaceDown(false, game);
            Cards cards = new CardsImpl();
            cards.add(copyFaceDown);
            Player player = game.getPlayer(faceDownCreature.getControllerId());
            controller.lookAtCards("face down card - " + mageObject.getName(), cards, game);
            if (player != null) {
                game.informPlayers(controller.getLogName() + " looks at a face down creature of " + player.getLogName());
            }
        } else {
            return false;
        }
        return true;
    }
}