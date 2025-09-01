package mage.cards.h;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
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
        this.addAbility(new EnchantAbility(auraTarget));

        // When Historian's Wisdom enters the battlefield, if enchanted permanent is a creature with the greatest power among creatures on the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(HistoriansWisdomCondition.instance).addHint(GreatestAmongPermanentsValue.POWER_ALL_CREATURES.getHint()));

        // As long as enchanted permanent is a creature, it gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 1), creatureCondition,
                "as long as enchanted permanent is a creature, it gets +2/+1"
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
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .filter(permanent -> permanent.isCreature(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .filter(x -> x >= GreatestAmongPermanentsValue.POWER_ALL_CREATURES.calculate(game, source, null))
                .isPresent();
    }

    @Override
    public String toString() {
        return "enchanted permanent is a creature with the greatest power among creatures on the battlefield";
    }
}
