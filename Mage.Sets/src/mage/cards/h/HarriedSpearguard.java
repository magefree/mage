package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarriedSpearguard extends CardImpl {

    public HarriedSpearguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Harried Spearguard dies, create a 1/1 black Rat creature token with "This creature can't block."
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new RatCantBlockToken())));
    }

    private HarriedSpearguard(final HarriedSpearguard card) {
        super(card);
    }

    @Override
    public HarriedSpearguard copy() {
        return new HarriedSpearguard(this);
    }
}
