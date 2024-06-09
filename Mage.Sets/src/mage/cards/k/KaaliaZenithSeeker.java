package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public final class KaaliaZenithSeeker extends CardImpl {

    public KaaliaZenithSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Kaalia, Zenith Seeker enters the battlefield, look at the top six cards of your library. You may reveal an Angel card, a Demon card, and/or a Dragon card from among them and put them into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KaaliaZenithSeekerEffect()));
    }

    private KaaliaZenithSeeker(final KaaliaZenithSeeker card) {
        super(card);
    }

    @Override
    public KaaliaZenithSeeker copy() {
        return new KaaliaZenithSeeker(this);
    }
}

class KaaliaZenithSeekerEffect extends OneShotEffect {

    private enum CreatureFinder {
        ANGEL(SubType.ANGEL),
        DEMON(SubType.DEMON),
        DRAGON(SubType.DRAGON);

        private final FilterCard filterCard;

        private CreatureFinder(SubType subType) {
            this.filterCard = new FilterCard("a " + subType.toString() + " card");
            this.filterCard.add(subType.getPredicate());
        }

        private TargetCard getTarget() {
            return new TargetCardInLibrary(0, 1, filterCard);
        }
    }

    KaaliaZenithSeekerEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top six cards of your library. "
                + "You may reveal an Angel card, a Demon card, and/or a Dragon card "
                + "from among them and put them into your hand. "
                + "Put the rest on the bottom of your library in a random order.";
    }

    private KaaliaZenithSeekerEffect(final KaaliaZenithSeekerEffect effect) {
        super(effect);
    }

    @Override
    public KaaliaZenithSeekerEffect copy() {
        return new KaaliaZenithSeekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        Cards toHand = new CardsImpl();
        for (CreatureFinder creatureFinder : CreatureFinder.values()) {
            TargetCard targetCard = creatureFinder.getTarget();
            if (player.choose(outcome, cards, targetCard, source, game)) {
                toHand.addAll(targetCard.getTargets());
            }
        }
        cards.removeAll(toHand);
        player.revealCards(source, toHand, game);
        player.moveCards(toHand, Zone.HAND, source, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
