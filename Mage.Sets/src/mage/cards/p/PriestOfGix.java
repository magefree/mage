
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class PriestOfGix extends CardImpl {

    public PriestOfGix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new BasicManaEffect(Mana.BlackMana(3))));
    }

    private PriestOfGix(final PriestOfGix card) {
        super(card);
    }

    @Override
    public PriestOfGix copy() {
        return new PriestOfGix(this);
    }
}
