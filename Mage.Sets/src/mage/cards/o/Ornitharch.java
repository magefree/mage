package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BirdToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Ornitharch extends CardImpl {

    public Ornitharch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Tribute 2
        this.addAbility(new TributeAbility(2));

        // When Ornitharch enters the battlefield, if tribute wasn't paid, create two 1/1 white Bird creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BirdToken(), 2))
                .withInterveningIf(TributeNotPaidCondition.instance));
    }

    private Ornitharch(final Ornitharch card) {
        super(card);
    }

    @Override
    public Ornitharch copy() {
        return new Ornitharch(this);
    }
}
