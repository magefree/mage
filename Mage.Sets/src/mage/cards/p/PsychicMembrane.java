
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class PsychicMembrane extends CardImpl {

    public PsychicMembrane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);
        this.addAbility(DefenderAbility.getInstance());
        
        // Whenever Psychic Membrane blocks, you may draw a card.
        this.addAbility(new BlocksSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
    }

    private PsychicMembrane(final PsychicMembrane card) {
        super(card);
    }

    @Override
    public PsychicMembrane copy() {
        return new PsychicMembrane(this);
    }
}
