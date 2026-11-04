package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;


import java.util.UUID;

/**
 *
 * @author notshauna
 */
public final class ExtractionIntegrant extends CardImpl {

    public ExtractionIntegrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Artifact creatures you control have menace and “Whenever a creature blocks this creature, that creature gets -1/-1 until end of turn.”
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE));
        ability.addEffect(new GainAbilityControlledEffect(
                new ExtractionIntegrantEffect(),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE)
                .setText("and \"Whenever a creature blocks this creature, that creature gets -1/-1 until end of turn.\""));
        this.addAbility(ability);
    }

    private ExtractionIntegrant(final ExtractionIntegrant card) {
        super(card);
    }

    @Override
    public ExtractionIntegrant copy() {
        return new ExtractionIntegrant(this);
    }
}

class ExtractionIntegrantEffect extends TriggeredAbilityImpl {

    public ExtractionIntegrantEffect() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Duration.EndOfTurn)
                .setText("Whenever a creature blocks this creature, that creature gets -1/-1 until end of turn."), false);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
                }
                return true;
            }
        }
        return false;
    }

    private ExtractionIntegrantEffect(final ExtractionIntegrantEffect effect) {
        super(effect);
    }

    @Override
    public ExtractionIntegrantEffect copy() {
        return new ExtractionIntegrantEffect(this);
    }
}