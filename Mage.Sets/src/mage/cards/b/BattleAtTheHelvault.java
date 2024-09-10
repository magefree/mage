package mage.cards.b;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.AvacynToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattleAtTheHelvault extends CardImpl {

    public BattleAtTheHelvault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- For each player, exile up to one target non-Saga, nonland permanent that player controls until Battle at the Helvault leaves the battlefield.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                (ability) -> {
                    ability.addEffect(new ExileUntilSourceLeavesEffect()
                            .setText("for each player, exile up to one target non-Saga, " +
                                    "nonland permanent that player controls until {this} leaves the battlefield")
                            .setTargetPointer(new EachTargetPointer()));
                    ability.setTargetAdjuster(BattleAtTheHelvaultAdjuster.instance);
                });

        // III -- Create Avacyn, a legendary 8/8 white Angel creature token with flying, vigilance, and indestructible.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateTokenEffect(new AvacynToken())
        );
        this.addAbility(sagaAbility);
    }

    private BattleAtTheHelvault(final BattleAtTheHelvault card) {
        super(card);
    }

    @Override
    public BattleAtTheHelvault copy() {
        return new BattleAtTheHelvault(this);
    }
}

enum BattleAtTheHelvaultAdjuster implements TargetAdjuster {
    instance;
    private static final Predicate<MageObject> predicate = Predicates.not(SubType.SAGA.getPredicate());

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID playerId : game.getState().getPlayersInRange(ability.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            FilterPermanent filter = new FilterNonlandPermanent(
                    "non-Saga, nonland permanent "
                            + (ability.isControlledBy(playerId) ? "you control" : "controlled by " + player.getName())
            );
            filter.add(predicate);
            filter.add(new ControllerIdPredicate(playerId));
            ability.addTarget(new TargetPermanent(0, 1, filter));
        }
    }
}