package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class YoungHeroRoleToken extends TokenImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 4));
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public YoungHeroRoleToken() {
        super("Young Hero", "Young Hero Role token");
        cardType.add(CardType.ENCHANTMENT);
        subtype.add(SubType.AURA);
        subtype.add(SubType.ROLE);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(ability);

        // Enchanted creature has "Whenever this creature attacks, if its toughness is 3 or less, put a +1/+1 counter on it."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new ConditionalInterveningIfTriggeredAbility(
                        new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())),
                        condition, "Whenever this creature attacks, if its toughness is 3 or less, put a +1/+1 counter on it."
                ), AttachmentType.AURA
        )));
    }

    private YoungHeroRoleToken(final YoungHeroRoleToken token) {
        super(token);
    }

    public YoungHeroRoleToken copy() {
        return new YoungHeroRoleToken(this);
    }
}
