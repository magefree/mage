
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author FenrisulfrX
 */
public final class SawtoothLoon extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("white or blue creature you control");

    static {
        Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE));
    }

    public SawtoothLoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sawtooth Loon enters the battlefield, return a white or blue creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), false));

        // When Sawtooth Loon enters the battlefield, draw two cards, then put two cards from your hand on the bottom of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SawtoothLoonEffect()));
    }

    private SawtoothLoon(final SawtoothLoon card) {
        super(card);
    }

    @Override
    public SawtoothLoon copy() {
        return new SawtoothLoon(this);
    }
}

class SawtoothLoonEffect extends OneShotEffect {

    public SawtoothLoonEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw two cards, then put two cards from your hand on the bottom of your library";
    }

    private SawtoothLoonEffect(final SawtoothLoonEffect effect) {
        super(effect);
    }

    @Override
    public SawtoothLoonEffect copy() {
        return new SawtoothLoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game);
            TargetCardInHand target = new TargetCardInHand(2, 2, new FilterCard());
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            Cards cardsToLibrary = new CardsImpl(target.getTargets());
            if (!cardsToLibrary.isEmpty()) {
                controller.putCardsOnBottomOfLibrary(cardsToLibrary, game, source, false);
            }
            return true;
        }
        return false;
    }

}
