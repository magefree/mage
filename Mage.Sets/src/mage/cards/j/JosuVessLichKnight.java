package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
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

;

public class JosuVessLichKnight extends CardImpl {

    public JosuVessLichKnight(UUID ownerID, CardSetInfo cardSetInfo){
        super(ownerID, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        supertype.add(SuperType.LEGENDARY);
        subtype.add(SubType.ZOMBIE, SubType.KNIGHT);
        power = new MageInt(4);
        toughness = new MageInt(5);

        addAbility(new MenaceAbility());
        addAbility(new KickerAbility("{5}{B}"));

        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieKnightToken(), 8), false);
        this.addAbility(new ConditionalTriggeredAbility(ability, KickedCondition.instance,
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
