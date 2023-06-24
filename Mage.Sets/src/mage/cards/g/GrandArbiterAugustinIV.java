package mage.cards.g;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GrandArbiterAugustinIV extends CardImpl {

    private static final FilterCard filterWhite = new FilterCard("White spells");
    private static final FilterCard filterBlue = new FilterCard("Blue spells");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public GrandArbiterAugustinIV(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // White spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filterWhite, 1)));

        // Blue spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filterBlue, 1)));

        // Spells your opponents cast cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasingAllEffect(1, new FilterCard("Spells"), TargetController.OPPONENT)));
    }

    private GrandArbiterAugustinIV(final GrandArbiterAugustinIV card) {
        super(card);
    }

    @Override
    public GrandArbiterAugustinIV copy() {
        return new GrandArbiterAugustinIV(this);
    }
}
