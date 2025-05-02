package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuidelightPathmaker extends CardImpl {

    public GuidelightPathmaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this Vehicle enters, you may search your library for an artifact card and reveal it. Put it onto the battlefield if its mana value is 2 or less. Otherwise, put it into your hand. Then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GuidelightPathmakerEffect(), true));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private GuidelightPathmaker(final GuidelightPathmaker card) {
        super(card);
    }

    @Override
    public GuidelightPathmaker copy() {
        return new GuidelightPathmaker(this);
    }
}

class GuidelightPathmakerEffect extends OneShotEffect {

    GuidelightPathmakerEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for an artifact card and reveal it. " +
                "Put it onto the battlefield if its mana value is 2 or less. " +
                "Otherwise, put it into your hand. If you search your library this way, shuffle";
    }

    private GuidelightPathmakerEffect(final GuidelightPathmakerEffect effect) {
        super(effect);
    }

    @Override
    public GuidelightPathmakerEffect copy() {
        return new GuidelightPathmakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card == null) {
            player.shuffleLibrary(source, game);
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (card.getManaValue() <= 2) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        } else {
            player.moveCards(card, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
