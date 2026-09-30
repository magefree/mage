package mage.cards.v;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SagaChapter;

/**
 * @author muz
 */
public final class VictoryOfThePyrohammer extends CardImpl {

    public VictoryOfThePyrohammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Victory of the Pyrohammer deals 4 damage to each creature and each planeswalker. Victory of the Pyrohammer gains "Damage isn't removed from creatures during cleanup steps."
        Ability gainedAbility = new SimpleStaticAbility(new VictoryOfThePyrohammerDamageEffect());
        Effects effects = new Effects();
        effects.add(new DamageAllEffect(4, StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER));
        effects.add(new GainAbilitySourceEffect(gainedAbility).setText("Victory of the Pyrohammer gains \"Damage isn't removed from creatures during cleanup steps.\""));
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            effects
        );

        // II, III -- Victory of the Pyrohammer deals 1 damage to each creature and each planeswalker.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
            new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER)
        );

        this.addAbility(sagaAbility);
    }

    private VictoryOfThePyrohammer(final VictoryOfThePyrohammer card) {
        super(card);
    }

    @Override
    public VictoryOfThePyrohammer copy() {
        return new VictoryOfThePyrohammer(this);
    }
}

class VictoryOfThePyrohammerDamageEffect extends ContinuousRuleModifyingEffectImpl {

    VictoryOfThePyrohammerDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "damage isn't removed from creatures during cleanup steps";
    }

    private VictoryOfThePyrohammerDamageEffect(final VictoryOfThePyrohammerDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REMOVE_DAMAGE_EOT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getPermanent(event.getTargetId()).isCreature();
    }

    @Override
    public VictoryOfThePyrohammerDamageEffect copy() {
        return new VictoryOfThePyrohammerDamageEffect(this);
    }
}
