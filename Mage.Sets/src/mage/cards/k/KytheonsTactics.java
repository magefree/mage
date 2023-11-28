
package mage.cards.k;

import java.util.UUID;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class KytheonsTactics extends CardImpl {

    public KytheonsTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{W}");

        // Creatures you control get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, those creatures also gain vigilance until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)),
                SpellMasteryCondition.instance,
                "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, those creatures also gain vigilance until end of turn"));
    }

    private KytheonsTactics(final KytheonsTactics card) {
        super(card);
    }

    @Override
    public KytheonsTactics copy() {
        return new KytheonsTactics(this);
    }
}
