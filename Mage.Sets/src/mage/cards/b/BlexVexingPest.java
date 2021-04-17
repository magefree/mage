package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author weirddan455
 */
public final class BlexVexingPest extends ModalDoubleFacesCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Pests, Bats, Insects, Snakes, and Spiders");

    static {
        filter.add(Predicates.or(
                SubType.PEST.getPredicate(),
                SubType.BAT.getPredicate(),
                SubType.INSECT.getPredicate(),
                SubType.SNAKE.getPredicate(),
                SubType.SPIDER.getPredicate()
        ));
    }

    public BlexVexingPest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PEST}, "{2}{G}",
                "Search for Blex", new CardType[]{CardType.SORCERY}, new SubType[]{}, "{2}{B}{B}"
        );

        // 1.
        // Blex, Vexing Pest
        // Legendary Creature - Pest
        this.getLeftHalfCard().addSuperType(SuperType.LEGENDARY);
        this.getLeftHalfCard().setPT(new MageInt(3), new MageInt(2));

        // Other Pests, Bats, Insects, Snakes, and Spiders you control get +1/+1.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)
        ));

        // When Blex, Vexing Pest dies, you gain 4 life.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(4)));

        // 2.
        // Search for Blex
        // Sorcery

        // Look at the top five cards of your library.
        // You may put any number of them into your hand and the rest into your graveyard.
        // You lose 3 life for each card put into your hand this way.
        this.getRightHalfCard().getSpellAbility().addEffect(new SearchForBlexEffect());
    }

    private BlexVexingPest(final BlexVexingPest card) {
        super(card);
    }

    @Override
    public BlexVexingPest copy() {
        return new BlexVexingPest(this);
    }
}

class SearchForBlexEffect extends LookLibraryControllerEffect {

    private static final FilterCard filter = new FilterCard("cards to put into your hand");

    public SearchForBlexEffect() {
        super(Outcome.DrawCard, StaticValue.get(5), false, Zone.GRAVEYARD, true);
        this.staticText = "Look at the top five cards of your library. "
                + "You may put any number of them into your hand and the rest into your graveyard. "
                + "You lose 3 life for each card you put into your hand this way";
    }

    private SearchForBlexEffect(final SearchForBlexEffect effect) {
        super(effect);
    }

    @Override
    public SearchForBlexEffect copy() {
        return new SearchForBlexEffect(this);
    }

    @Override
    protected void actionWithSelectedCards(Cards cards, Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCard target = new TargetCard(0, 5, Zone.LIBRARY, filter);
            if (player.choose(outcome, cards, target, game)) {
                Cards pickedCards = new CardsImpl(target.getTargets());
                cards.removeAll(pickedCards);
                player.moveCards(pickedCards, Zone.HAND, source, game);
                player.loseLife(pickedCards.size() * 3, game, source, false);
            }
        }
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }
}
