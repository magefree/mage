package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BirdToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoulOfMigration extends CardImpl {

    public SoulOfMigration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Soul of Migration enters the battlefield, create two 1/1 white Bird creature tokens with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BirdToken(), 2)));

        // Evoke {3}{W}
        this.addAbility(new EvokeAbility("{3}{W}"));
    }

    private SoulOfMigration(final SoulOfMigration card) {
        super(card);
    }

    @Override
    public SoulOfMigration copy() {
        return new SoulOfMigration(this);
    }
}
