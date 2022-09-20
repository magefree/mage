package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.CastAsThoughItHadFlashIfConditionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TimelyWard extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(CommanderPredicate.instance);
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public TimelyWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // You may cast this spell as though it had flash if it targets a commander.
        this.addAbility(new CastAsThoughItHadFlashIfConditionAbility(condition,
                "You may cast this spell as though it had flash if it targets a commander."
        ));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                IndestructibleAbility.getInstance(), AttachmentType.AURA
        )));
    }

    private TimelyWard(final TimelyWard card) {
        super(card);
    }

    @Override
    public TimelyWard copy() {
        return new TimelyWard(this);
    }
}
