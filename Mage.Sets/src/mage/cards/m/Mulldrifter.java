
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Mulldrifter extends CardImpl {

    public Mulldrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Mulldrifter enters the battlefield, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2)));
        // Evoke {2}{U}
        this.addAbility(new EvokeAbility("{2}{U}"));
    }

    private Mulldrifter(final Mulldrifter card) {
        super(card);
    }

    @Override
    public Mulldrifter copy() {
        return new Mulldrifter(this);
    }
}
