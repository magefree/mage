package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.PlaneswalkEffect;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.CascadeAbility;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Skiwkr
 */
public final class TARDIS extends CardImpl {
    private static final FilterControlledPermanent TimeLordFilter = new FilterControlledPermanent();

    static {TimeLordFilter.add(SubType.TIME_LORD.getPredicate());}
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(TimeLordFilter, ComparisonType.MORE_THAN, 1);

    private static final Hint hint = new ConditionHint(condition, "You control a Time Lord");

    public TARDIS(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());

        // Whenever TARDIS attacks, if you control a Time Lord, the next spell you cast this turn has cascade, and you may planeswalk.
        Ability TardisAttackTrigger = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new NextSpellCastHasAbilityEffect(new CascadeAbility()), false),
                new PermanentsOnTheBattlefieldCondition(TimeLordFilter),
                "Whenever TARDIS attacks, if you control a Time Lord, the next spell you cast this turn has cascade, and you may planeswalk.");
        TardisAttackTrigger.addEffect(new PlaneswalkEffect(true));
		this.addAbility(TardisAttackTrigger);
        // Crew 2
        this.addAbility(new CrewAbility(2));

        this.addAbility(new CastOnlyIfConditionIsTrueAbility(condition).addHint(hint));

    }

    private TARDIS(final TARDIS card) {
        super(card);
    }

    @Override
    public TARDIS copy() {
        return new TARDIS(this);
    }
}
