package mage.cards.i;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class IanTheReckless extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(ModifiedPredicate.instance);
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public IanTheReckless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Ian the Reckless attacks, if it's modified, you may have it deal damage equal to its power to you and any target.
        TriggeredAbility ability = new AttacksTriggeredAbility(new DamageControllerEffect(
                new SourcePermanentPowerCount()).setText("have it deal damage equal to its power to you"), true);
        ability.addEffect(new DamageTargetEffect(new SourcePermanentPowerCount()).setText("and any target"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, condition,
                "Whenever {this} attacks, if it's modified, you may have it deal damage equal to its power to you and any target."));
    }

    private IanTheReckless(final IanTheReckless card) {
        super(card);
    }

    @Override
    public IanTheReckless copy() {
        return new IanTheReckless(this);
    }
}
