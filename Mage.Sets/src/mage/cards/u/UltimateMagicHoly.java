package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltimateMagicHoly extends CardImpl {

    public UltimateMagicHoly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Permanents you control gain indestructible until end of turn. If this spell was cast from exile, prevent all damage that would be dealt to you this turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENTS
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new PreventDamageToControllerEffect(Duration.EndOfTurn)),
                UltimateMagicHolyCondition.instance, "If this spell was cast from exile, " +
                "prevent all damage that would be dealt to you this turn"
        ));

        // Foretell {2}{W}
        this.addAbility(new ForetellAbility(this, "{2}{W}"));
    }

    private UltimateMagicHoly(final UltimateMagicHoly card) {
        super(card);
    }

    @Override
    public UltimateMagicHoly copy() {
        return new UltimateMagicHoly(this);
    }
}

enum UltimateMagicHolyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getSpell)
                .map(Spell::getFromZone)
                .map(Zone.EXILED::match)
                .orElse(false);
    }
}
