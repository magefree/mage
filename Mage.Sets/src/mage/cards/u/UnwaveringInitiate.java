
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.EmbalmAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class UnwaveringInitiate extends CardImpl {

    public UnwaveringInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Embalm {4}{W}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{4}{W}"), this));
    }

    private UnwaveringInitiate(final UnwaveringInitiate card) {
        super(card);
    }

    @Override
    public UnwaveringInitiate copy() {
        return new UnwaveringInitiate(this);
    }
}
