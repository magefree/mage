package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScorpionSeethingStriker extends CardImpl {

    public ScorpionSeethingStriker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCORPION);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // At the beginning of your end step, if a creature died this turn, target creature you control connives.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new ConniveTargetEffect()).withInterveningIf(MorbidCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(MorbidHint.instance));
    }

    private ScorpionSeethingStriker(final ScorpionSeethingStriker card) {
        super(card);
    }

    @Override
    public ScorpionSeethingStriker copy() {
        return new ScorpionSeethingStriker(this);
    }
}
