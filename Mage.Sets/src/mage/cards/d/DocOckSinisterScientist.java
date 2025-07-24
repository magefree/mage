package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DocOckSinisterScientist extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(8);
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VILLAIN);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition2 = new PermanentsOnTheBattlefieldCondition(filter);

    public DocOckSinisterScientist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // As long as there are eight or more cards in your graveyard, Doc Ock has base power and toughness 8/8.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new SetBasePowerToughnessSourceEffect(8, 8, Duration.WhileOnBattlefield), condition,
                "as long as there are eight or more cards in your graveyard, {this} has base power and toughness 8/8"
        )));

        // As long as you control another Villain, Doc Ock has hexproof.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()), condition2,
                "as long as you control another Villain, {this} has hexproof"
        )));
    }

    private DocOckSinisterScientist(final DocOckSinisterScientist card) {
        super(card);
    }

    @Override
    public DocOckSinisterScientist copy() {
        return new DocOckSinisterScientist(this);
    }
}
