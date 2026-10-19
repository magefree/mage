package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public final class CruciasTitanOfTheWaves extends CardImpl {

    public CruciasTitanOfTheWaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.YOU,
                new CruciasTitanOfTheWavesEffect(), false));
    }

    private CruciasTitanOfTheWaves(final CruciasTitanOfTheWaves card) {
        super(card);
    }

    @Override
    public CruciasTitanOfTheWaves copy() {
        return new CruciasTitanOfTheWaves(this);
    }
}

class CruciasTitanOfTheWavesEffect extends OneShotEffect {

    CruciasTitanOfTheWavesEffect() {
        super(Outcome.Benefit);
        staticText = "you may discard a card. If you do, create a Treasure token and choose ambitious or expedient. If you chose ambitious, seek a card with greater mana value than the discarded card. If you chose expedient, seek a card with lesser mana value than the discarded card";
    }

    private CruciasTitanOfTheWavesEffect(final CruciasTitanOfTheWavesEffect effect) {
        super(effect);
    }

    @Override
    public CruciasTitanOfTheWavesEffect copy() {
        return new CruciasTitanOfTheWavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(outcome, "Discard a card?", source, game)) {
            return false;
        }
        Card discarded = player.discard(1, false, false, source, game).getCards(game).stream().findFirst().orElse(null);
        if (discarded == null) {
            return false;
        }
        new CreateTokenEffect(new TreasureToken()).apply(game, source);
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose ambitious or expedient");
        choice.setChoices(new HashSet<>(Arrays.asList("ambitious", "expedient")));
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate("ambitious".equals(choice.getChoice())
                ? ComparisonType.MORE_THAN : ComparisonType.FEWER_THAN, discarded.getManaValue()));
        return player.seekCard(filter, source, game);
    }
}
