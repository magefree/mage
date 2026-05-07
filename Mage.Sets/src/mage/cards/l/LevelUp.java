package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class LevelUp extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 10));
    }

    public LevelUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, put a +1/+1 counter on enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "enchanted creature"), false));

        // Enchanted creature has "Whenever this creature attacks, double the number of +1/+1 counters on it. Then if it has power 10 or greater, draw a card."
        AttacksTriggeredAbility attacksAbility = new AttacksTriggeredAbility(new DoubleCountersSourceEffect(CounterType.P1P1), false);
        attacksAbility.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new SourceMatchesFilterCondition("its power is 10 or greater", filter),
                "Then if it has power 10 or greater, draw a card"
        ));
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAttachedEffect(attacksAbility, AttachmentType.AURA)
        ));
    }

    private LevelUp(final LevelUp card) {
        super(card);
    }

    @Override
    public LevelUp copy() {
        return new LevelUp(this);
    }
}
