package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class AndraditeLeech extends CardImpl {

    private static final FilterCard filter = new FilterCard("Black spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public AndraditeLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Black spells you cast cost {B} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostIncreasingAllEffect(new ManaCostsImpl<>("{B}"), filter, TargetController.YOU)));
        // {B}: Andradite Leech gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{B}")));
    }

    private AndraditeLeech(final AndraditeLeech card) {
        super(card);
    }

    @Override
    public AndraditeLeech copy() {
        return new AndraditeLeech(this);
    }
}
