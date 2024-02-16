
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.game.permanent.token.PatagiaViperSnakeToken;

/**
 *
 * @author emerald000
 */
public final class PatagiaViper extends CardImpl {

    public PatagiaViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Patagia Viper enters the battlefield, create two 1/1 green and blue Snake creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PatagiaViperSnakeToken(), 2), false));

        // When Patagia Viper enters the battlefield, sacrifice it unless {U} was spent to cast it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessConditionEffect(ManaWasSpentCondition.BLUE), false));
    }

    private PatagiaViper(final PatagiaViper card) {
        super(card);
    }

    @Override
    public PatagiaViper copy() {
        return new PatagiaViper(this);
    }
}
