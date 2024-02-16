package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PhyrexianMiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StingingHivemaster extends CardImpl {

    public StingingHivemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // When Stinging Hivemaster dies, create a 1/1 colorless Phyrexian Mite artifact creature token with toxic 1 and "This creature can't block."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PhyrexianMiteToken())));
    }

    private StingingHivemaster(final StingingHivemaster card) {
        super(card);
    }

    @Override
    public StingingHivemaster copy() {
        return new StingingHivemaster(this);
    }
}
