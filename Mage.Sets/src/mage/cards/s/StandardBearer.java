
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.TargetsHaveToTargetPermanentIfAbleEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StandardBearer extends CardImpl {

    public StandardBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.FLAGBEARER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // While choosing targets as part of casting a spell or activating an ability, your opponents must choose at least one Flagbearer on the battlefield if able.
        this.addAbility(new SimpleStaticAbility(new TargetsHaveToTargetPermanentIfAbleEffect()));
    }

    private StandardBearer(final StandardBearer card) {
        super(card);
    }

    @Override
    public StandardBearer copy() {
        return new StandardBearer(this);
    }
}
