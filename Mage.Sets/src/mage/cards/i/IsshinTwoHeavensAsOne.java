package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.AdditionalTriggersAttackingReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IsshinTwoHeavensAsOne extends CardImpl {

    public IsshinTwoHeavensAsOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a creature attacking causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggersAttackingReplacementEffect(false)));
    }

    private IsshinTwoHeavensAsOne(final IsshinTwoHeavensAsOne card) {
        super(card);
    }

    @Override
    public IsshinTwoHeavensAsOne copy() {
        return new IsshinTwoHeavensAsOne(this);
    }
}
