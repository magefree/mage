
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

/**
 * @author Loki
 */
public final class MysticSnake extends CardImpl {

    public MysticSnake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}{U}");
        this.subtype.add(SubType.SNAKE);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FlashAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new CounterTargetEffect());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private MysticSnake(final MysticSnake card) {
        super(card);
    }

    @Override
    public MysticSnake copy() {
        return new MysticSnake(this);
    }
}
