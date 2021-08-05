package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarnheimAspirant extends CardImpl {

    private static final FilterCard filter = new FilterCard("Angel spells");

    static {
        filter.add(SubType.ANGEL.getPredicate());
    }

    public StarnheimAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Angel spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)));
    }

    private StarnheimAspirant(final StarnheimAspirant card) {
        super(card);
    }

    @Override
    public StarnheimAspirant copy() {
        return new StarnheimAspirant(this);
    }
}
