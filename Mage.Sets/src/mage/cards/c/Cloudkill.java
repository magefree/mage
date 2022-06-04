package mage.cards.c;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GreatestCommanderManaValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Cloudkill extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(GreatestCommanderManaValue.instance);

    public Cloudkill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // All creatures gets -X/-X until end of turn, where X is the greatest mana value of a commander you own on the battlefield or in the command zone.
        this.getSpellAbility().addEffect(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn));
        this.getSpellAbility().addHint(GreatestCommanderManaValue.getHint());
    }

    private Cloudkill(final Cloudkill card) {
        super(card);
    }

    @Override
    public Cloudkill copy() {
        return new Cloudkill(this);
    }
}
