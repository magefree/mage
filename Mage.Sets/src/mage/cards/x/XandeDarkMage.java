package mage.cards.x;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class XandeDarkMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public XandeDarkMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Xande gets +1/+1 for each noncreature, nonland card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));
    }

    private XandeDarkMage(final XandeDarkMage card) {
        super(card);
    }

    @Override
    public XandeDarkMage copy() {
        return new XandeDarkMage(this);
    }
}
