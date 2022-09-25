
package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.ZombieKnightToken;

import java.util.UUID;

public final class JosuVessLichKnight extends CardImpl {

    public JosuVessLichKnight(UUID ownerID, CardSetInfo cardSetInfo){
        super(ownerID, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE, SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        //Kicker {5}{B} (You may pay an additional {5}{B} as you cast this spell.)
        this.addAbility(new KickerAbility("{5}{B}"));

        //Menace
        this.addAbility(new MenaceAbility(false));

        //When Josu Vess, Lich Knight enters the battlefield, if it was kicked, create eight 2/2 black Zombie Knight creature tokens with menace.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieKnightToken(), 8));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE,
                "When {this} enters the battlefield, if it was kicked, create eight 2/2 black Zombie Knight creature tokens with menace."));
    }

    public JosuVessLichKnight(final JosuVessLichKnight josuVessLichKnight){
        super(josuVessLichKnight);
    }

    @Override
    public Card copy() {
        return new JosuVessLichKnight(this);
    }
}
