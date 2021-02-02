
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class VedalkenHeretic extends CardImpl {

    public VedalkenHeretic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ROGUE);



        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Vedalken Heretic deals damage to an opponent, you may draw a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1), true));

    }

    private VedalkenHeretic(final VedalkenHeretic card) {
        super(card);
    }

    @Override
    public VedalkenHeretic copy() {
        return new VedalkenHeretic(this);
    }
}
