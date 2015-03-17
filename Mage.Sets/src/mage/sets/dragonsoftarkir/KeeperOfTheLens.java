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
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
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
public class KeeperOfTheLens extends CardImpl {

    public KeeperOfTheLens(UUID ownerId) {
        super(ownerId, 240, "Keeper of the Lens", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Golem");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // You may look at face-down creatures you don't control.
        // TODO: this should be a static abilitie and not use activated abilities (because it could than be restriced)
        this.addAbility(new KeeperOfTheLensLookFaceDownAbility());        
    }

    public KeeperOfTheLens(final KeeperOfTheLens card) {
        super(card);
    }

    @Override
    public KeeperOfTheLens copy() {
        return new KeeperOfTheLens(this);
    }
}

class KeeperOfTheLensLookFaceDownAbility extends ActivatedAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face down creature you don't control");

    static {
        filter.add(new FaceDownPredicate());
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public KeeperOfTheLensLookFaceDownAbility() {
        super(Zone.BATTLEFIELD, new KeeperOfTheLensLookFaceDownEffect(), new GenericManaCost(0));
        this.usesStack = false;
        this.addTarget(new TargetCreaturePermanent(filter));
    }

    public KeeperOfTheLensLookFaceDownAbility(KeeperOfTheLensLookFaceDownAbility ability) {
        super(ability);
    }

    @Override
    public KeeperOfTheLensLookFaceDownAbility copy() {
        return new KeeperOfTheLensLookFaceDownAbility(this);
    }

}

class KeeperOfTheLensLookFaceDownEffect extends OneShotEffect {

    public KeeperOfTheLensLookFaceDownEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may look at face-down creatures you don't control";
    }

    public KeeperOfTheLensLookFaceDownEffect(final KeeperOfTheLensLookFaceDownEffect effect) {
        super(effect);
    }

    @Override
    public KeeperOfTheLensLookFaceDownEffect copy() {
        return new KeeperOfTheLensLookFaceDownEffect(this);
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
            controller.lookAtCards("face down card - " + mageObject.getLogName(), cards, game);
            if (player != null) {
                game.informPlayers(controller.getName() + " looks at a face down creature of " + player.getName());
            }
        } else {
            return false;
        }
        return true;
    }
}
