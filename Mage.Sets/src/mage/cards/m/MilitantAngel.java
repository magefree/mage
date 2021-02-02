package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class MilitantAngel extends CardImpl {

    public MilitantAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Militant Angel enters the battlefield, create a number of 2/2 white Knight creature tokens with vigilance equal to the number of opponents you attacked this turn.
        Effect effect = new CreateTokenEffect(new KnightToken(), AttackedThisTurnOpponentsCount.instance);
        effect.setText("create a number of 2/2 white Knight creature tokens with vigilance equal to the number of opponents you attacked this turn");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
    }

    private MilitantAngel(final MilitantAngel card) {
        super(card);
    }

    @Override
    public MilitantAngel copy() {
        return new MilitantAngel(this);
    }
}
