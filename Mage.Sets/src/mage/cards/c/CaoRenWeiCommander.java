
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class CaoRenWeiCommander extends CardImpl {

    public CaoRenWeiCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // When Cao Ren, Wei Commander enters the battlefield, you lose 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(3)));
    }

    private CaoRenWeiCommander(final CaoRenWeiCommander card) {
        super(card);
    }

    @Override
    public CaoRenWeiCommander copy() {
        return new CaoRenWeiCommander(this);
    }
}
