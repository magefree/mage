
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class ApothecaryInitiate extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ApothecaryInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player casts a white spell, you may pay {1}. If you do, you gain 1 life.
        this.addAbility(new SpellCastAllTriggeredAbility(new DoIfCostPaid(new GainLifeEffect(1), new ManaCostsImpl<>("{1}")), filter, false));

    }

    private ApothecaryInitiate(final ApothecaryInitiate card) {
        super(card);
    }

    @Override
    public ApothecaryInitiate copy() {
        return new ApothecaryInitiate(this);
    }
}
