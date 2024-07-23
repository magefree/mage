package mage.cards.i;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 * @author Cguy7777
 */
public final class Idolized extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("nonland permanents you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Nonland permanents you control", xValue);

    public Idolized(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has "Whenever this creature attacks alone, it gets +X/+X until end of turn, where X is the number of nonland permanents you control."
        Ability attacksAloneAbility = new AttacksAloneSourceTriggeredAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
        ).setTriggerPhrase("Whenever this creature attacks alone, ").addHint(hint);

        this.addAbility(
                new SimpleStaticAbility(
                        Zone.BATTLEFIELD,
                        new GainAbilityAttachedEffect(attacksAloneAbility, AttachmentType.AURA)
                ).addHint(hint));
    }

    private Idolized(final Idolized card) {
        super(card);
    }

    @Override
    public Idolized copy() {
        return new Idolized(this);
    }
}
