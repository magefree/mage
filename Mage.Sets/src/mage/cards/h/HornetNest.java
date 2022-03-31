package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectDeathToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HornetNest extends CardImpl {

    public HornetNest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Hornet Nest is dealt damage, create that many 1/1 green Insect creature tokens with flying and deathtouch.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new CreateTokenEffect(new InsectDeathToken(), SavedDamageValue.MANY), false));
    }

    private HornetNest(final HornetNest card) {
        super(card);
    }

    @Override
    public HornetNest copy() {
        return new HornetNest(this);
    }
}
