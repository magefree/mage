
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCounteredControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BaralChiefOfCompliance extends CardImpl {

    private static final FilterCard filter = new FilterCard("Instant and sorcery spells");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public BaralChiefOfCompliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever a spell or ability you control counters a spell, you may draw a card. If you do, discard a card.
        this.addAbility(new SpellCounteredControllerTriggeredAbility(new DrawDiscardControllerEffect(true)));
    }

    private BaralChiefOfCompliance(final BaralChiefOfCompliance card) {
        super(card);
    }

    @Override
    public BaralChiefOfCompliance copy() {
        return new BaralChiefOfCompliance(this);
    }
}
