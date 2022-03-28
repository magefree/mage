package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class HistoriansWisdom extends CardImpl {

    private static final Condition creatureCondition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public HistoriansWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When Historian's Wisdom enters the battlefield, if enchanted permanent is a creature with the greatest power among creatures on the battlefield, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                HistoriansWisdomCondition.instance,
                "When {this} enters the battlefield, if enchanted permanent is a creature with the greatest power among creatures on the battlefield, draw a card."
        ));

        // As long as enchanted permanent is a creature, it gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 1),
                creatureCondition,
                "As long as enchanted permanent is a creature, it gets +2/+1"
        )));
    }

    private HistoriansWisdom(final HistoriansWisdom card) {
        super(card);
    }

    @Override
    public HistoriansWisdom copy() {
        return new HistoriansWisdom(this);
    }
}

enum HistoriansWisdomCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        game.applyEffects(); // Make sure +2/+1 buff gets applied first
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment == null) {
            return false;
        }
        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        if (creature == null) {
            return false;
        }
        if (!creature.isCreature(game)) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, creature.getPower().getValue()));
        return game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).isEmpty();
    }
}
