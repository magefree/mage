package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BishopOfWings extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ANGEL, "an Angel");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.ANGEL, "an Angel you control");

    public BishopOfWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever an Angel enters the battlefield under your control, you gain 4 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(4), filter));

        // Whenever an Angel you control dies, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new SpiritWhiteToken()), false, filter2
        ));
    }

    private BishopOfWings(final BishopOfWings card) {
        super(card);
    }

    @Override
    public BishopOfWings copy() {
        return new BishopOfWings(this);
    }
}
