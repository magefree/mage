package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KinjallisDawnrunner extends CardImpl {

    public KinjallisDawnrunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When Kinjalli's Dawnrunner enters the battlefield, it explores.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExploreSourceEffect()));
    }

    private KinjallisDawnrunner(final KinjallisDawnrunner card) {
        super(card);
    }

    @Override
    public KinjallisDawnrunner copy() {
        return new KinjallisDawnrunner(this);
    }
}
