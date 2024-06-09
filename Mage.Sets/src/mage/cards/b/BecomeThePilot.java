package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BecomeThePilot extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("noncommander creature");

    static {
        filter.add(Predicates.not(CommanderPredicate.instance));
    }

    public BecomeThePilot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant noncommander creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(new ControlEnchantedEffect()));

        //  Enchanted creature gets +2/+2 and can't be blocked unless it's attacking its owner or a permanent its owner controls.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBeBlockedAttachedEffect(AttachmentType.AURA), BecomeThePilotCondition.instance,
                "and can't be blocked unless it's attacking its owner or a permanent its owner controls"
        ));
        this.addAbility(ability.addHint(BecomeThePilotCondition.getHint()));
    }

    private BecomeThePilot(final BecomeThePilot card) {
        super(card);
    }

    @Override
    public BecomeThePilot copy() {
        return new BecomeThePilot(this);
    }
}

enum BecomeThePilotCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "Enchanted creature is not " +
            "attacking its owner or a permanent its owner controls"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        UUID defenderId = game.getCombat().getDefenderId(permanent.getId());
        return defenderId == null
                || (!permanent.isOwnedBy(defenderId) && !permanent.isOwnedBy(game.getControllerId(defenderId)));
    }
}