package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneyardMycodrax extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("other creature cards");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public BoneyardMycodrax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Boneyard Mycodrax's power and toughness are each equal to the number of other creature cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue, Duration.EndOfGame)));

        // Scavenge {4}{B}
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{4}{B}")));

    }

    private BoneyardMycodrax(final BoneyardMycodrax card) {
        super(card);
    }

    @Override
    public BoneyardMycodrax copy() {
        return new BoneyardMycodrax(this);
    }
}
