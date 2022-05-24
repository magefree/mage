package mage.cards.a;

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
 * @author LoneFox
 */
public final class AlabasterLeech extends CardImpl {

    private static final FilterCard filter = new FilterCard("White spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public AlabasterLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // White spells you cast cost {W} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostIncreasingAllEffect(new ManaCostsImpl<>("{W}"), filter, TargetController.YOU)));
    }

    private AlabasterLeech(final AlabasterLeech card) {
        super(card);
    }

    @Override
    public AlabasterLeech copy() {
        return new AlabasterLeech(this);
    }
}
