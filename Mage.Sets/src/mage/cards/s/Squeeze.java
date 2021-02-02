package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Squeeze extends CardImpl {

    private static final FilterCard filter = new FilterCard("Sorcery spells");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public Squeeze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Sorcery spells cost {3} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostIncreasingAllEffect(3, filter, TargetController.ANY))
        );
    }

    private Squeeze(final Squeeze card) {
        super(card);
    }

    @Override
    public Squeeze copy() {
        return new Squeeze(this);
    }
}
