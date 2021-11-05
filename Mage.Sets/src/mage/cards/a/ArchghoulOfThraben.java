package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class ArchghoulOfThraben extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ZOMBIE);

    public ArchghoulOfThraben(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Archghoul of Thraben or another Zombie you control dies, look at the top card of your library.
        // If it's a Zombie card, you may reveal it and put it into your hand.
        // If you don't put the card into your hand, you may put it into your graveyard.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new ArchghoulOfThrabenEffect(), false, filter));
    }

    private ArchghoulOfThraben(final ArchghoulOfThraben card) {
        super(card);
    }

    @Override
    public ArchghoulOfThraben copy() {
        return new ArchghoulOfThraben(this);
    }
}

class ArchghoulOfThrabenEffect extends OneShotEffect {

    public ArchghoulOfThrabenEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. " +
                "If it's a Zombie card, you may reveal it and put it into your hand. " +
                "If you don't put the card into your hand, you may put it into your graveyard";
    }

    private ArchghoulOfThrabenEffect(final ArchghoulOfThrabenEffect effect) {
        super(effect);
    }

    @Override
    public ArchghoulOfThrabenEffect copy() {
        return new ArchghoulOfThrabenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }
        controller.lookAtCards("Top card of library", topCard, game);
        if (topCard.hasSubtype(SubType.ZOMBIE, game)) {
            if (controller.chooseUse(Outcome.DrawCard, "Reveal " + topCard.getName() + " and put it into your hand?", source, game)) {
                controller.revealCards(source, new CardsImpl(topCard), game);
                controller.moveCards(topCard, Zone.HAND, source, game);
                return true;
            }
        }
        if (controller.chooseUse(Outcome.Neutral, "Put " + topCard.getName() + " into your graveyard?", source, game)) {
            controller.moveCards(topCard, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
