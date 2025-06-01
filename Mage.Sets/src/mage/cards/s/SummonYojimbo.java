package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonYojimbo extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, enchantment, or tapped creature an opponent controls");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                Predicates.and(
                        TappedPredicate.TAPPED,
                        CardType.CREATURE.getPredicate()
                )
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SummonYojimbo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I - Exile target artifact, enchantment, or tapped creature an opponent controls.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new ExileTargetEffect(), new TargetPermanent(filter)
        );

        // II, III - Until your next turn, creatures can't attack you unless their controller pays {2} for each of those creatures.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new CantAttackYouUnlessPayAllEffect(Duration.UntilYourNextTurn, new GenericManaCost(2))
                        .setText("until your next turn, creatures can't attack you unless their controller pays {2} for each of those creatures")
        );

        // IV - Create X Treasure tokens, where X is the number of opponents who control a creature with power 4 or greater.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new CreateTokenEffect(new TreasureToken(), SummonYojimboValue.instance)
        );
        this.addAbility(sagaAbility);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private SummonYojimbo(final SummonYojimbo card) {
        super(card);
    }

    @Override
    public SummonYojimbo copy() {
        return new SummonYojimbo(this);
    }
}

enum SummonYojimboValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(Controllable::getControllerId)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public SummonYojimboValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of opponents who control a creature with power 4 or greater";
    }

    @Override
    public String toString() {
        return "X";
    }
}
