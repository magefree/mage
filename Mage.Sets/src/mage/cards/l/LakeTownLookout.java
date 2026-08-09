package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LakeTownLookout extends CardImpl {

    public LakeTownLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature dies, recruit.
        this.addAbility(new DiesSourceTriggeredAbility(new RecruitEffect()));
    }

    private LakeTownLookout(final LakeTownLookout card) {
        super(card);
    }

    @Override
    public LakeTownLookout copy() {
        return new LakeTownLookout(this);
    }
}
