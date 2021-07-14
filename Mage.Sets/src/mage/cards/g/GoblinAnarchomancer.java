package mage.cards.g;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinAnarchomancer extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.GREEN)
        ));
    }

    public GoblinAnarchomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each spell you cast that's red or green costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)
                .setText("each spell you cast that's red or green costs {1} less to cast")));
    }

    private GoblinAnarchomancer(final GoblinAnarchomancer card) {
        super(card);
    }

    @Override
    public GoblinAnarchomancer copy() {
        return new GoblinAnarchomancer(this);
    }
}
