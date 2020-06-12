package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ValorousSteed extends CardImpl {

    public ValorousSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Valorous Steed enters the battlefield, create a 2/2 white Knight creature token with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightToken())));
    }

    private ValorousSteed(final ValorousSteed card) {
        super(card);
    }

    @Override
    public ValorousSteed copy() {
        return new ValorousSteed(this);
    }
}
