package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonBahamut extends CardImpl {

    public SummonBahamut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{9}");

        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II -- Destroy up to one target nonland permanent.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new DestroyTargetEffect(), new TargetNonlandPermanent(0, 1));

        // III -- Draw two cards.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new DrawCardSourceControllerEffect(2));

        // IV -- Mega Flare -- This creature deals damage equal to the total mana value of other permanents you control to each opponent.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_IV, ability -> {
            ability.addEffect(new DamagePlayersEffect(SummonBahamutValue.instance, TargetController.OPPONENT)
                    .setText("{this} deals damage equal to the total mana value of other permanents you control to each opponent"));
            ability.withFlavorWord("Mega Flare");
            ability.addHint(SummonBahamutValue.getHint());
        });
        this.addAbility(sagaAbility);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SummonBahamut(final SummonBahamut card) {
        super(card);
    }

    @Override
    public SummonBahamut copy() {
        return new SummonBahamut(this);
    }
}

enum SummonBahamutValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Hint hint = new ValueHint("Total mana value among other permanents you control", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .sum();
    }

    @Override
    public SummonBahamutValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
