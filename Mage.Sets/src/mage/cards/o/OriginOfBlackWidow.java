package mage.cards.o;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class OriginOfBlackWidow extends CardImpl {

    public OriginOfBlackWidow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Each opponent sacrifices a creature of their choice.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
        );

        // II -- Creatures you control gain deathtouch until end of turn.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
            new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
            )
        );

        // III -- Each opponent loses 1 life for each creature card in their graveyard.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new OriginOfBlackWidowEffect());

        this.addAbility(sagaAbility);
    }

    private OriginOfBlackWidow(final OriginOfBlackWidow card) {
        super(card);
    }

    @Override
    public OriginOfBlackWidow copy() {
        return new OriginOfBlackWidow(this);
    }
}

class OriginOfBlackWidowEffect extends OneShotEffect {

    OriginOfBlackWidowEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses 1 life for each creature card in their graveyard";
    }

    private OriginOfBlackWidowEffect(final OriginOfBlackWidowEffect effect) {
        super(effect);
    }

    @Override
    public OriginOfBlackWidowEffect copy() {
        return new OriginOfBlackWidowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.loseLife(
                    player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game),
                    game, source, false
                );
            }
        }
        return true;
    }
}
