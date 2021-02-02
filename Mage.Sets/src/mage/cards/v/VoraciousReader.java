
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class VoraciousReader extends CardImpl {

    private static final FilterCard filter = new FilterCard("Instant and sorcery spells");
    static {
        filter.add(Predicates.or(
            CardType.INSTANT.getPredicate(),
            CardType.SORCERY.getPredicate()
        ));
    }

    public VoraciousReader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Prowess
        this.addAbility(new ProwessAbility());

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private VoraciousReader(final VoraciousReader card) {
        super(card);
    }

    @Override
    public VoraciousReader copy() {
        return new VoraciousReader(this);
    }
}
