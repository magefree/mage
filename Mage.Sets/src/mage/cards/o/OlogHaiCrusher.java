package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OlogHaiCrusher extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(Predicates.or(
                SubType.GOBLIN.getPredicate(),
                SubType.ORC.getPredicate()
        ));
    }

    private static final Condition condition =
            new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);
    private static final Hint hint = new ConditionHint(
            new PermanentsOnTheBattlefieldCondition(filter), "You control a Goblin or Orc"
    );

    public OlogHaiCrusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Olog-hai Crusher can't block unless you control a Goblin or Orc.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield),
                condition, "{this} can't block unless you control a Goblin or Orc"
        )).addHint(hint));
    }

    private OlogHaiCrusher(final OlogHaiCrusher card) {
        super(card);
    }

    @Override
    public OlogHaiCrusher copy() {
        return new OlogHaiCrusher(this);
    }
}
