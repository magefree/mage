package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class WalkingDream extends CardImpl {

    private static final String rule = "{this} doesn't untap during your untap step if an opponent controls two or more creatures.";

    public WalkingDream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Walking Dream is unblockable.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Walking Dream doesn't untap during your untap step if an opponent controls two or more creatures.
        ContinuousRuleModifyingEffect dontUntap = new DontUntapInControllersUntapStepSourceEffect(false, true);
        dontUntap.setText(rule);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousRuleModifyingEffect(
                        dontUntap,
                        new OpponentControlsPermanentCondition(
                                new FilterCreaturePermanent(),
                                ComparisonType.MORE_THAN, 1)));
        this.addAbility(ability);

    }

    private WalkingDream(final WalkingDream card) {
        super(card);
    }

    @Override
    public WalkingDream copy() {
        return new WalkingDream(this);
    }
}
