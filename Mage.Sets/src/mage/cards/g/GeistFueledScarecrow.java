package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class GeistFueledScarecrow extends CardImpl {

    private static final FilterCard filter = new FilterCard("Creature spells");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public GeistFueledScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Creature spells you cast cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostIncreasingAllEffect(1, filter, TargetController.YOU)));
    }

    private GeistFueledScarecrow(final GeistFueledScarecrow card) {
        super(card);
    }

    @Override
    public GeistFueledScarecrow copy() {
        return new GeistFueledScarecrow(this);
    }
}
