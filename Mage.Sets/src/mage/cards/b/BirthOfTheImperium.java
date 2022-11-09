package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.WhiteAstartesWarriorToken;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BirthOfTheImperium extends CardImpl {

    public BirthOfTheImperium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{U}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Create a 2/2 white Astartes Warrior creature token with vigilance for each opponent you have.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new CreateTokenEffect(new WhiteAstartesWarriorToken(), OpponentsCount.instance)
                        .setText("create a 2/2 white Astartes Warrior creature " +
                                "token with vigilance for each opponent you have")
        );

        // II -- Each opponent sacrifices a creature.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
        );

        // III -- Draw two cards for each opponent who controls fewer creatures than you.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new DrawCardSourceControllerEffect(BirthOfTheImperiumValue.instance)
        );
        this.addAbility(sagaAbility);
    }

    private BirthOfTheImperium(final BirthOfTheImperium card) {
        super(card);
    }

    @Override
    public BirthOfTheImperium copy() {
        return new BirthOfTheImperium(this);
    }
}

enum BirthOfTheImperiumValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE,
                        sourceAbility.getControllerId(), sourceAbility, game
                ).stream()
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
        int yourCreatures = map.getOrDefault(sourceAbility.getControllerId(), 0);
        return yourCreatures > 0 ? 2 * game
                .getOpponents(sourceAbility.getControllerId())
                .stream().mapToInt(uuid -> map.getOrDefault(uuid, 0))
                .filter(x -> x < yourCreatures)
                .sum() : 0;
    }

    @Override
    public BirthOfTheImperiumValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "opponent who controls fewer creatures than you";
    }

    @Override
    public String toString() {
        return "two";
    }
}
