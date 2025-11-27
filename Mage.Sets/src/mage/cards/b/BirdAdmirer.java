package mage.cards.b;

import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BirdAdmirer extends TransformingDoubleFacedCard {

    public BirdAdmirer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ARCHER, SubType.WEREWOLF}, "{2}{G}",
                "Wing Shredder",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G");

        // Bird Admirer
        this.getLeftHalfCard().setPT(1, 4);

        // Reach
        this.getLeftHalfCard().addAbility(ReachAbility.getInstance());

        // Daybound
        this.getLeftHalfCard().addAbility(new DayboundAbility());

        // Wing Shredder
        this.getRightHalfCard().setPT(3, 5);

        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // Nightbound
        this.getRightHalfCard().addAbility(new NightboundAbility());
    }

    private BirdAdmirer(final BirdAdmirer card) {
        super(card);
    }

    @Override
    public BirdAdmirer copy() {
        return new BirdAdmirer(this);
    }
}
