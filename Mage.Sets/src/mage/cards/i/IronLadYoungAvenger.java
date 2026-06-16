package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class IronLadYoungAvenger extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature spells");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public IronLadYoungAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Noncreature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private IronLadYoungAvenger(final IronLadYoungAvenger card) {
        super(card);
    }

    @Override
    public IronLadYoungAvenger copy() {
        return new IronLadYoungAvenger(this);
    }
}
