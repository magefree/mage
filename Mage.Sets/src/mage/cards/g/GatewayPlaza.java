package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class GatewayPlaza extends CardImpl {

    public GatewayPlaza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.GATE);

        // Gateway Plaza enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Gateway Plaza enters the battlefield, sacrifice it unless you pay {1}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(
                        new GenericManaCost(1)
                ), false
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    public GatewayPlaza(final GatewayPlaza card) {
        super(card);
    }

    @Override
    public GatewayPlaza copy() {
        return new GatewayPlaza(this);
    }
}
