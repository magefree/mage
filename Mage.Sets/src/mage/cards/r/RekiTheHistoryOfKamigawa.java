
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;

/**
 *
 * @author Loki
 */
public final class RekiTheHistoryOfKamigawa extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a legendary spell");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public RekiTheHistoryOfKamigawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        // Whenever you cast a legendary spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter, false));
    }

    private RekiTheHistoryOfKamigawa(final RekiTheHistoryOfKamigawa card) {
        super(card);
    }

    @Override
    public RekiTheHistoryOfKamigawa copy() {
        return new RekiTheHistoryOfKamigawa(this);
    }
}
