package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ThaliaTheSurvivor extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature spells");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ThaliaTheSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Noncreature spells your opponents cast cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostIncreasingAllEffect(1, filter, TargetController.OPPONENT)));
    }

    private ThaliaTheSurvivor(final ThaliaTheSurvivor card) {
        super(card);
    }

    @Override
    public ThaliaTheSurvivor copy() {
        return new ThaliaTheSurvivor(this);
    }
}
