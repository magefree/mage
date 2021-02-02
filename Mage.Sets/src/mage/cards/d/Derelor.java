package mage.cards.d;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class Derelor extends CardImpl {

    private static final FilterCard filter = new FilterCard("Black spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public Derelor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Black spells you cast cost {B} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasingAllEffect(new ManaCostsImpl<>("{B}"), filter, TargetController.YOU)));
    }

    private Derelor(final Derelor card) {
        super(card);
    }

    @Override
    public Derelor copy() {
        return new Derelor(this);
    }
}
