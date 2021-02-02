
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.TargetsHaveToTargetPermanentIfAbleEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * Cardname: Coalition Honor Guard
 *
 * @author LevelX2
 */
public final class CoalitionHonorGuard extends CardImpl {

    public CoalitionHonorGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FLAGBEARER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // While choosing targets as part of casting a spell or activating an ability, your opponents must choose at least one Flagbearer on the battlefield if able.
        this.addAbility(new SimpleStaticAbility(new TargetsHaveToTargetPermanentIfAbleEffect()));
    }

    private CoalitionHonorGuard(final CoalitionHonorGuard card) {
        super(card);
    }

    @Override
    public CoalitionHonorGuard copy() {
        return new CoalitionHonorGuard(this);
    }
}
