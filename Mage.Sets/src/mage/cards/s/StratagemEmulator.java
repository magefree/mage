package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class StratagemEmulator extends CardImpl {

    public StratagemEmulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature dies, create X 1/1 colorless Borg artifact creature tokens, where X is its power.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new BorgToken(), SourcePermanentPowerValue.NOT_NEGATIVE)));
    }

    private StratagemEmulator(final StratagemEmulator card) {
        super(card);
    }

    @Override
    public StratagemEmulator copy() {
        return new StratagemEmulator(this);
    }
}
