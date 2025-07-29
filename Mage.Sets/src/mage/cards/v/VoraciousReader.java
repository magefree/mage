
package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VoraciousReader extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("Instant and sorcery spells");

    public VoraciousReader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Prowess
        this.addAbility(new ProwessAbility());

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private VoraciousReader(final VoraciousReader card) {
        super(card);
    }

    @Override
    public VoraciousReader copy() {
        return new VoraciousReader(this);
    }
}
