package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class FolioOfFancies extends CardImpl {

    public FolioOfFancies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        // Players have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET, TargetController.ANY
        )));

        // {X}{X}, {T}: Each player draws X cards.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardAllEffect(ManacostVariableValue.instance), new ManaCostsImpl("{X}{X}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {2}{U}, {T}: Each opponent puts a number of cards equal to the number of cards in their hand from the top of their library into their graveyard.
        ability = new SimpleActivatedAbility(new FolioOfFanciesEffect(), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FolioOfFancies(final FolioOfFancies card) {
        super(card);
    }

    @Override
    public FolioOfFancies copy() {
        return new FolioOfFancies(this);
    }
}

class FolioOfFanciesEffect extends OneShotEffect {

    FolioOfFanciesEffect() {
        super(Outcome.Benefit);
        staticText = "Each opponent puts a number of cards equal to the number of cards in their hand " +
                "from the top of their library into their graveyard.";
    }

    private FolioOfFanciesEffect(final FolioOfFanciesEffect effect) {
        super(effect);
    }

    @Override
    public FolioOfFanciesEffect copy() {
        return new FolioOfFanciesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .filter(player -> !player.getHand().isEmpty())
                .map(player -> player.getLibrary().getTopCards(game, player.getHand().size()))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return controller.moveCards(cards, Zone.GRAVEYARD, source, game);
    }
}