package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 * @author muz
 */
public final class CurzonDaxBoundByBlood extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
        new FilterControlledCreaturePermanent("another Klingon or Warrior you control");
    private static final FilterCreatureCard filter2 =
        new FilterCreatureCard("a creature card with mana value 3 or less");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(SubType.KLINGON.getPredicate(), SubType.WARRIOR.getPredicate()));
        filter2.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public CurzonDaxBoundByBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TRILL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever another Klingon or Warrior you control dies, Curzon Dax deals 1 damage to each opponent.
        this.addAbility(new DiesCreatureTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), false, filter));

        // When Curzon Dax dies, you may put a creature card with mana value 3 or less from your hand onto the battlefield.
        this.addAbility(new DiesSourceTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(filter2)));
    }

    private CurzonDaxBoundByBlood(final CurzonDaxBoundByBlood card) {
        super(card);
    }

    @Override
    public CurzonDaxBoundByBlood copy() {
        return new CurzonDaxBoundByBlood(this);
    }
}
