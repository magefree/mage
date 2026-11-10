package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author muz
 */
public final class RavenousCosmozoan extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);
    private static final Hint hint = new ValueHint("Noncreature, nonland cards in your graveyard", xValue);

    public RavenousCosmozoan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {1} less to cast for each noncreature, nonland card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).setRuleAtTheTop(true).addHint(hint));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());
    }

    private RavenousCosmozoan(final RavenousCosmozoan card) {
        super(card);
    }

    @Override
    public RavenousCosmozoan copy() {
        return new RavenousCosmozoan(this);
    }
}
