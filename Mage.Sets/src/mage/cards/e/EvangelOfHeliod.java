package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EvangelOfHeliod extends CardImpl {

    public EvangelOfHeliod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Evangel of Heliod enters the battlefield, create a number of 1/1 white Soldier creature tokens equal to your devotion to white.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new SoldierToken(), DevotionCount.W)
                        .setText("create a number of 1/1 white Soldier creature tokens equal to your devotion to white")
        ).addHint(DevotionCount.W.getHint()));
    }

    private EvangelOfHeliod(final EvangelOfHeliod card) {
        super(card);
    }

    @Override
    public EvangelOfHeliod copy() {
        return new EvangelOfHeliod(this);
    }
}
