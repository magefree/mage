
package mage.cards.k;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KeeperOfTheLens extends CardImpl {

    public KeeperOfTheLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // You may look at face-down creatures you don't control.
        // TODO: this should be a static abilitie and not use activated abilities (because it could than be restriced)
        this.addAbility(new KeeperOfTheLensLookFaceDownAbility());
    }

    private KeeperOfTheLens(final KeeperOfTheLens card) {
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
        filter.add(FaceDownPredicate.instance);
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
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
        this.staticText = "You may look at face-down creatures you don't control any time";
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
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (controller == null || mageObject == null) {
            return false;
        }
        Permanent faceDownCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (faceDownCreature != null) {
            Permanent copyFaceDown = faceDownCreature.copy();
            copyFaceDown.setFaceDown(false, game);
            Cards cards = new CardsImpl(copyFaceDown);
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
