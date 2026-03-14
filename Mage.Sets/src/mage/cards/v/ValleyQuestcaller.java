package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author Grath
 */
public final class ValleyQuestcaller extends CardImpl {

    static final FilterPermanent filter = new FilterControlledPermanent("other Rabbits, Bats, Birds, and/or Mice");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
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
                new ScryEffect(1, false), filter, TargetController.YOU
        ));

        // Other Rabbits, Bats, Birds, and Mice you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ).setText("other Rabbits, Bats, Birds, and Mice you control get +1/+1")));
    }

    private ValleyQuestcaller(final ValleyQuestcaller card) {
        super(card);
    }

    @Override
    public ValleyQuestcaller copy() {
        return new ValleyQuestcaller(this);
    }
}
