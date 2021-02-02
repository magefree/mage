
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class EidolonOfBlossoms extends CardImpl {

    public EidolonOfBlossoms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Constellation - Whenever Eidolon of Blossoms or another enchantment enters the battlefield under your control, draw a card.
        this.addAbility(new ConstellationAbility(new DrawCardSourceControllerEffect(1)));
    }

    private EidolonOfBlossoms(final EidolonOfBlossoms card) {
        super(card);
    }

    @Override
    public EidolonOfBlossoms copy() {
        return new EidolonOfBlossoms(this);
    }
}
