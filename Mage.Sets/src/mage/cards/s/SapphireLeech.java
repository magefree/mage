package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class SapphireLeech extends CardImpl {

    private static final FilterCard filter = new FilterCard("Blue spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public SapphireLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Blue spells you cast cost {U} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostIncreasingAllEffect(new ManaCostsImpl<>("{U}"), filter, TargetController.YOU)));
    }

    private SapphireLeech(final SapphireLeech card) {
        super(card);
    }

    @Override
    public SapphireLeech copy() {
        return new SapphireLeech(this);
    }
}
