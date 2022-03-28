
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LevelX2
 */
public final class ProfanerOfTheDead extends CardImpl {

    public ProfanerOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Profaner of the Dead exploits a creature, return to their owners' hands all creatures your opponents control with toughness less than the exploited creature's toughness.
        this.addAbility(new ExploitCreatureTriggeredAbility(new ProfanerOfTheDeadReturnEffect(), false, SetTargetPointer.PERMANENT));

    }

    private ProfanerOfTheDead(final ProfanerOfTheDead card) {
        super(card);
    }

    @Override
    public ProfanerOfTheDead copy() {
        return new ProfanerOfTheDead(this);
    }
}

class ProfanerOfTheDeadReturnEffect extends OneShotEffect {

    public ProfanerOfTheDeadReturnEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return to their owners' hands all creatures your opponents control with toughness less than the exploited creature's toughness";
    }

    public ProfanerOfTheDeadReturnEffect(final ProfanerOfTheDeadReturnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent exploitedCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && exploitedCreature != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(TargetController.OPPONENT.getControllerPredicate());
            filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, exploitedCreature.getToughness().getValue()));
            Cards cardsToHand = new CardsImpl();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                cardsToHand.add(permanent);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;

    }

    @Override
    public ProfanerOfTheDeadReturnEffect copy() {
        return new ProfanerOfTheDeadReturnEffect(this);
    }
}
