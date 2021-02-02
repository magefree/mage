
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;

/**
 *
 * @author noxx
 */
public final class LunarMystic extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant spell");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public LunarMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant spell, you may pay {1}. If you do, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new GenericManaCost(1)), filter, false));
    }

    private LunarMystic(final LunarMystic card) {
        super(card);
    }

    @Override
    public LunarMystic copy() {
        return new LunarMystic(this);
    }
}
