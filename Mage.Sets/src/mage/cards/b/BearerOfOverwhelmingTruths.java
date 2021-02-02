
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class BearerOfOverwhelmingTruths extends CardImpl {

    public BearerOfOverwhelmingTruths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setBlue(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Bearer of Overwhelming Truths deals combat damage to a player, investigate.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new InvestigateEffect(), false));
    }

    private BearerOfOverwhelmingTruths(final BearerOfOverwhelmingTruths card) {
        super(card);
    }

    @Override
    public BearerOfOverwhelmingTruths copy() {
        return new BearerOfOverwhelmingTruths(this);
    }
}
