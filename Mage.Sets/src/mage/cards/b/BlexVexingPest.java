package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.Cards;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

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

class SearchForBlexEffect extends LookLibraryAndPickControllerEffect {

    SearchForBlexEffect() {
        super(5, Integer.MAX_VALUE, PutCards.HAND, PutCards.GRAVEYARD);
        this.optional = true;
    }

    private SearchForBlexEffect(final SearchForBlexEffect effect) {
        super(effect);
    }

    @Override
    public SearchForBlexEffect copy() {
        return new SearchForBlexEffect(this);
    }

    @Override
    protected boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards, Cards otherCards) {
        super.actionWithPickedCards(game, source, player, pickedCards, otherCards);
        player.loseLife(pickedCards.size() * 3, game, source, false);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode).concat(". You lose 3 life for each card you put into your hand this way");
    }
}
