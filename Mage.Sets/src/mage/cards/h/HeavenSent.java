package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class HeavenSent extends CardImpl {

    public HeavenSent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Investigate.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new InvestigateEffect()
        );

        // III -- Heaven Sent deals 1 damage to each opponent. Then if an opponent has 0 or less life, draw seven cards. Otherwise, exile Heaven Sent and you may cast it this turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                new HeavenSentEffect()
        );

        this.addAbility(sagaAbility);
    }

    private HeavenSent(final HeavenSent card) {
        super(card);
    }

    @Override
    public HeavenSent copy() {
        return new HeavenSent(this);
    }
}

class HeavenSentEffect extends OneShotEffect {

    HeavenSentEffect() {
        super(Outcome.Benefit);
        staticText = "Then if an opponent has 0 or less life, draw seven cards. "
                + "Otherwise, exile {this} and you may cast it this turn.";
    }

    private HeavenSentEffect(final HeavenSentEffect effect) {
        super(effect);
    }

    @Override
    public HeavenSentEffect copy() {
        return new HeavenSentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        boolean condition = game
                .getOpponents(player.getId())
                .stream()
                .map(id -> game.getPlayer(id))
                .filter(Objects::nonNull)
                .anyMatch(p -> p.getLife() <= 0);

        if (condition) {
            return new DrawCardSourceControllerEffect(7)
                    .apply(game, source);
        } else {
            Permanent heaven = source.getSourcePermanentIfItStillExists(game);
            if (heaven == null) {
                return false;
            }

            player.moveCardsToExile(heaven, source, game, true, null, "");
            if (game.getState().getZone(heaven.getId()) == Zone.EXILED) {
                game.addEffect(
                        new PlayFromNotOwnHandZoneTargetEffect(
                                Zone.EXILED, TargetController.YOU, Duration.EndOfTurn,
                                false, true
                        ).setTargetPointer(new FixedTarget(heaven, game)),
                        source
                );
            }
            return true;
        }
    }
}