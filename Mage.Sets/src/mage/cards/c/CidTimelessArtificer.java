package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CidTimelessArtificer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SubType.HERO.getPredicate()
        ));
        filter2.add(SubType.ARTIFICER.getPredicate());
    }

    private static final DynamicValue xValue = new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ARTIFICER)),
            new CardsInControllerGraveyardCount(filter2)
    );
    private static final Hint hint = new ValueHint(
            "Artificers you control and Artificer cards in your graveyard", xValue
    );

    public CidTimelessArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Artifact creatures and Heroes you control get +1/+1 for each Artificer you control and each Artificer card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                xValue, xValue, Duration.WhileOnBattlefield, filter, false
        ).setText("artifact creatures and Heroes you control get +1/+1 " +
                "for each Artificer you control and each Artificer card in your graveyard")).addHint(hint));

        // A deck can have any number of cards named Cid, Timeless Artificer.
        this.getSpellAbility().addEffect(new InfoEffect("a deck can have any number of cards named Cid, Timeless Artificer"));

        // Cycling {W}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{W}{U}")));
    }

    private CidTimelessArtificer(final CidTimelessArtificer card) {
        super(card);
    }

    @Override
    public CidTimelessArtificer copy() {
        return new CidTimelessArtificer(this);
    }
}
