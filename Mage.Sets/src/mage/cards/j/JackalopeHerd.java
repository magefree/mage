
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class JackalopeHerd extends CardImpl {

    public JackalopeHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When you cast a spell, return Jackalope Herd to its owner's hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ReturnToHandSourceEffect(true), StaticFilters.FILTER_SPELL_A, false)
                .setTriggerPhrase("When you cast a spell, "));
    }

    private JackalopeHerd(final JackalopeHerd card) {
        super(card);
    }

    @Override
    public JackalopeHerd copy() {
        return new JackalopeHerd(this);
    }
}
