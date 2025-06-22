package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SnowHound extends CardImpl {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("green or blue creature");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.BLUE)
        ));
    }

    public SnowHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, {tap}: Return Snow Hound and target green or blue creature you control to their owner's hand.
        Ability ability = new SimpleActivatedAbility(new ReturnToHandSourceEffect(true).setText("Return {this}"), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ReturnToHandTargetEffect().setText("and target green or blue creature you control to their owner's hand"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SnowHound(final SnowHound card) {
        super(card);
    }

    @Override
    public SnowHound copy() {
        return new SnowHound(this);
    }
}
