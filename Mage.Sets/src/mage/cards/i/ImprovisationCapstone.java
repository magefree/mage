package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ParadigmAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImprovisationCapstone extends CardImpl {

    public ImprovisationCapstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        this.subtype.add(SubType.LESSON);

        // Exile cards from the top of your library until you exile cards with total mana value 4 or greater. You may cast any number of spells from among them without paying their mana costs.
        this.getSpellAbility().addEffect(new ImprovisationCapstoneEffect());

        // Paradigm
        this.addAbility(new ParadigmAbility());
    }

    private ImprovisationCapstone(final ImprovisationCapstone card) {
        super(card);
    }

    @Override
    public ImprovisationCapstone copy() {
        return new ImprovisationCapstone(this);
    }
}

class ImprovisationCapstoneEffect extends OneShotEffect {

    ImprovisationCapstoneEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile cards " +
                "with total mana value 4 or greater. You may cast any number of spells " +
                "from among them without paying their mana costs.";
    }

    private ImprovisationCapstoneEffect(final ImprovisationCapstoneEffect effect) {
        super(effect);
    }

    @Override
    public ImprovisationCapstoneEffect copy() {
        return new ImprovisationCapstoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            player.moveCards(card, Zone.EXILED, source, game);
            game.processAction();
            if (cards
                    .getCards(game)
                    .stream()
                    .mapToInt(MageObject::getManaValue)
                    .sum() >= 4) {
                break;
            }
        }
        CardUtil.castMultipleWithAttributeForFree(player, source, game, cards, StaticFilters.FILTER_CARD);
        return true;
    }
}
