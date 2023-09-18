package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritRedToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiftOfWrath extends CardImpl {

    private static final Condition condition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public GiftOfWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // As long as enchanted permanent is a creature, it gets +2/+2 and has menace.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 2), condition,
                "as long as enchanted permanent is a creature, it gets +2/+2"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.AURA
        ), condition, "and has menace. <i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);

        // When Gift of Wrath leaves the battlefield, create a 2/2 red Spirit creature token with menace.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritRedToken()), false));
    }

    private GiftOfWrath(final GiftOfWrath card) {
        super(card);
    }

    @Override
    public GiftOfWrath copy() {
        return new GiftOfWrath(this);
    }
}
