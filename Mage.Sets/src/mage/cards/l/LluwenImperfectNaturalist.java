package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.BlackGreenWormToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LluwenImperfectNaturalist extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND);
    private static final Hint hint = new ValueHint("Land cards in your graveyard", xValue);

    public LluwenImperfectNaturalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B/G}{B/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Lluwen enters, mill four cards, then you may put a creature or land card from among the milled cards on top of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LluwenImperfectNaturalistEffect()));

        // {2}{B/G}{B/G}{B/G}, {T}, Discard a land card: Create a 1/1 black and green Worm creature token for each land card in your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new BlackGreenWormToken(), xValue), new ManaCostsImpl<>("{2}{B/G}{B/G}{B/G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND)));
        this.addAbility(ability.addHint(hint));
    }

    private LluwenImperfectNaturalist(final LluwenImperfectNaturalist card) {
        super(card);
    }

    @Override
    public LluwenImperfectNaturalist copy() {
        return new LluwenImperfectNaturalist(this);
    }
}

class LluwenImperfectNaturalistEffect extends OneShotEffect {

    LluwenImperfectNaturalistEffect() {
        super(Outcome.Benefit);
        staticText = "mill four cards, then you may put a creature or land card " +
                "from among the milled cards on top of your library";
    }

    private LluwenImperfectNaturalistEffect(final LluwenImperfectNaturalistEffect effect) {
        super(effect);
    }

    @Override
    public LluwenImperfectNaturalistEffect copy() {
        return new LluwenImperfectNaturalistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(4, source, game);
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCard(
                0, 1, Zone.ALL, StaticFilters.FILTER_CARD_CREATURE_OR_LAND
        );
        player.choose(Outcome.DrawCard, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.putCardsOnTopOfLibrary(card, game, source, false);
        }
        return true;
    }
}
