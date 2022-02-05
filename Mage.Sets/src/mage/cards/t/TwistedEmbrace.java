package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwistedEmbrace extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final Condition condition
            = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public TwistedEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When Twisted Embrace enters the battlefield, destroy target creature or planeswalker an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // As long as enchanted permanent is a creature, it gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(1, 1), condition,
                "as long as enchanted permanent is a creature, it gets +1/+1"
        )));
    }

    private TwistedEmbrace(final TwistedEmbrace card) {
        super(card);
    }

    @Override
    public TwistedEmbrace copy() {
        return new TwistedEmbrace(this);
    }
}
