package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author Grath
 */
public final class DisplacedDinosaurs extends CardImpl {

    public DisplacedDinosaurs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // As a historic permanent enters the battlefield under your control, it becomes a 7/7 Dinosaur creature in
        // addition to its other types.
        this.addAbility(new SimpleStaticAbility(new DisplacedDinosaursEntersBattlefieldEffect()));
    }

    private DisplacedDinosaurs(final DisplacedDinosaurs card) {
        super(card);
    }

    @Override
    public DisplacedDinosaurs copy() {
        return new DisplacedDinosaurs(this);
    }
}

class DisplacedDinosaursEntersBattlefieldEffect extends ReplacementEffectImpl {

    DisplacedDinosaursEntersBattlefieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "As a historic permanent enters the battlefield under your control, it becomes a 7/7 Dinosaur creature in addition to its other types. <i>(Artifacts, legendaries, and Sagas are historic.)</i>";
    }

    private DisplacedDinosaursEntersBattlefieldEffect(mage.cards.d.DisplacedDinosaursEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent historic = ((EntersTheBattlefieldEvent) event).getTarget();
        return historic != null && historic.isControlledBy(source.getControllerId())
                && historic.isHistoric(game)
                && !event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent historic = ((EntersTheBattlefieldEvent) event).getTarget();
        if (historic != null) {
            TargetPointer historicTarget = new FixedTarget(historic.getId(), historic.getZoneChangeCounter(game) + 1);
            ContinuousEffect creatureEffect = new AddCardTypeTargetEffect(Duration.Custom, CardType.CREATURE);
            creatureEffect.setTargetPointer(historicTarget);
            game.addEffect(creatureEffect, source);
            ContinuousEffect dinosaurEffect = new AddCardSubTypeTargetEffect(SubType.DINOSAUR, Duration.Custom);
            dinosaurEffect.setTargetPointer(historicTarget);
            game.addEffect(dinosaurEffect, source);
            ContinuousEffect sevenSevenEffect = new SetBasePowerToughnessTargetEffect(7, 7, Duration.Custom);
            sevenSevenEffect.setTargetPointer(historicTarget);
            game.addEffect(sevenSevenEffect, source);
        }
        return false;
    }

    @Override
    public mage.cards.d.DisplacedDinosaursEntersBattlefieldEffect copy() {
        return new mage.cards.d.DisplacedDinosaursEntersBattlefieldEffect(this);
    }
}