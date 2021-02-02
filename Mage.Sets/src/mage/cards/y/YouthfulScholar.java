
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class YouthfulScholar extends CardImpl {

    public YouthfulScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Youthful Scholar dies, draw two cards.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(2), false));
    }

    private YouthfulScholar(final YouthfulScholar card) {
        super(card);
    }

    @Override
    public YouthfulScholar copy() {
        return new YouthfulScholar(this);
    }
}
