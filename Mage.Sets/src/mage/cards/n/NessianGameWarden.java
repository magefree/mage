
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class NessianGameWarden extends CardImpl {

    public NessianGameWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Nessian Game Warden enters the battlefield, look at the top X cards of your library, where X is the number of forests you control. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NessianGameWardenEffect(), false));
    }

    private NessianGameWarden(final NessianGameWarden card) {
        super(card);
    }

    @Override
    public NessianGameWarden copy() {
        return new NessianGameWarden(this);
    }
}

class NessianGameWardenEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("forests you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public NessianGameWardenEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top X cards of your library, where X is the number of Forests you control. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order";
    }

    public NessianGameWardenEffect(final NessianGameWardenEffect effect) {
        super(effect);
    }

    @Override
    public NessianGameWardenEffect copy() {
        return new NessianGameWardenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || sourcePermanent == null) {
            return false;
        }

        int count = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
        Cards cards = new CardsImpl();
        cards.addAll(controller.getLibrary().getTopCards(game, count));
        controller.lookAtCards(sourcePermanent.getIdName(), cards, game);

        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCreatureCard("creature card to put into your hand"));
            if (target.canChoose(controller.getId(), source, game) && controller.choose(Outcome.DrawCard, cards, target, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.revealCards(sourcePermanent.getName(), new CardsImpl(card), game);
                    cards.remove(card);
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
        }

        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
