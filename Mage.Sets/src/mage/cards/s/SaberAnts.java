package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author LoneFox
 *
 */
public final class SaberAnts extends CardImpl {

    public SaberAnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Saber Ants is dealt damage, you may create that many 1/1 green Insect creature tokens.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new CreateTokenEffect(new InsectToken(), SavedDamageValue.MANY), true));
    }

    private SaberAnts(final SaberAnts card) {
        super(card);
    }

    @Override
    public SaberAnts copy() {
        return new SaberAnts(this);
    }
}
