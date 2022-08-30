package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.HasSubtypesSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TajuruParagon extends CardImpl {

    public TajuruParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Tajuru Paragon is also a Cleric, Rogue, Warrior, and Wizard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new HasSubtypesSourceEffect(
                SubType.CLERIC, SubType.ROGUE, SubType.WARRIOR, SubType.WIZARD
        )));

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // When Tajuru Paragon enters the battlefield, if it was kicked, reveal the top six cards of your library. You may put a card that shares a creature type with it from among them into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new TajuruParagonEffect()),
                KickedCondition.ONCE, "When {this} enters the battlefield, " +
                "if it was kicked, reveal the top six cards of your library. " +
                "You may put a card that shares a creature type with it from among them into your hand. " +
                "Put the rest on the bottom of your library in a random order."
        ));
    }

    private TajuruParagon(final TajuruParagon card) {
        super(card);
    }

    @Override
    public TajuruParagon copy() {
        return new TajuruParagon(this);
    }
}

class TajuruParagonEffect extends OneShotEffect {

    TajuruParagonEffect() {
        super(Outcome.Benefit);
    }

    private TajuruParagonEffect(final TajuruParagonEffect effect) {
        super(effect);
    }

    @Override
    public TajuruParagonEffect copy() {
        return new TajuruParagonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        player.revealCards(source, cards, game);
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent != null) {
            FilterCard filter = new FilterCard("card that shares a creature type with " + permanent.getName());
            filter.add(new SharesCreatureTypePredicate(permanent));
            TargetCard target = new TargetCardInLibrary(0, 1, filter);
            player.choose(outcome, cards, target, game);
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                player.moveCards(card, Zone.HAND, source, game);
                cards.remove(card);
            }
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
