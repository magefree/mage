
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ExultantCultist extends CardImpl {

    public ExultantCultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Exultant Cultist dies, draw a card.
        this.addAbility(new DiesTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    public ExultantCultist(final ExultantCultist card) {
        super(card);
    }

    @Override
    public ExultantCultist copy() {
        return new ExultantCultist(this);
    }
}
