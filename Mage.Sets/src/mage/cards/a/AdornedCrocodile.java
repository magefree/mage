package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.RenewAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.ZombieDruidToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdornedCrocodile extends CardImpl {

    public AdornedCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When this creature dies, create a 2/2 black Zombie Druid creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ZombieDruidToken())));

        // Renew -- {B}, Exile this card from your graveyard: Put a +1/+1 counter on target creature. Activate only as a sorcery.
        this.addAbility(new RenewAbility("{B}", CounterType.P1P1.createInstance()));
    }

    private AdornedCrocodile(final AdornedCrocodile card) {
        super(card);
    }

    @Override
    public AdornedCrocodile copy() {
        return new AdornedCrocodile(this);
    }
}
