package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author Grath
 */
public final class ValleyQuestcaller extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other Rabbits, Bats, Birds, and/or Mice");
    static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Rabbits, Bats, Birds, and Mice");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                SubType.RABBIT.getPredicate(),
                SubType.BAT.getPredicate(),
                SubType.BIRD.getPredicate(),
                SubType.MOUSE.getPredicate()
        ));
        filter2.add(Predicates.or(
                SubType.RABBIT.getPredicate(),
                SubType.BAT.getPredicate(),
                SubType.BIRD.getPredicate(),
                SubType.MOUSE.getPredicate()
        ));
    }

    public ValleyQuestcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever one or more other Rabbits, Bats, Birds, and/or Mice you control enter, scry 1.
        this.addAbility(new EntersBattlefieldOneOrMoreTriggeredAbility(
                new ScryEffect(1, false),
                filter,
                TargetController.YOU)
        );

        // Other Rabbits, Bats, Birds, and Mice you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter2, true
        )));
    }

    private ValleyQuestcaller(final ValleyQuestcaller card) {
        super(card);
    }

    @Override
    public ValleyQuestcaller copy() {
        return new ValleyQuestcaller(this);
    }
}
