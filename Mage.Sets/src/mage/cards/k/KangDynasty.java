package mage.cards.k;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class KangDynasty extends CardImpl {

    public KangDynasty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures. Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                ability -> {
                    ability.addEffect(new TapTargetEffect()
                            .setText("For each opponent, tap up to one target creature that player controls"));
                    ability.addEffect(new GoadTargetEffect()
                            .withTargetDescription("those creatures"));
                    ability.addEffect(new KangDynastyEffect());

                    ability.getEffects().setTargetPointer(new EachTargetPointer());
                    ability.addTarget(new TargetCreaturePermanent(0, 1));
                    ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
                }
        );

        // III -- Target creature you control gets +1/+1 until end of turn for each card in your hand and can't be blocked this turn.
        Effects effects = new Effects();
        effects.add(new BoostTargetEffect(CardsInControllerHandCount.ANY_SINGULAR, CardsInControllerHandCount.ANY_SINGULAR, Duration.EndOfTurn));
        effects.add(new CantBeBlockedTargetEffect()
                .setText("and can't be blocked this turn"));
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                effects,
                new TargetControlledCreaturePermanent()
        );

        this.addAbility(sagaAbility);
    }

    private KangDynasty(final KangDynasty card) {
        super(card);
    }

    @Override
    public KangDynasty copy() {
        return new KangDynasty(this);
    }
}

class KangDynastyEffect extends OneShotEffect {

    KangDynastyEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card";
    }

    private KangDynastyEffect(final KangDynastyEffect effect) {
        super(effect);
    }

    @Override
    public KangDynastyEffect copy() {
        return new KangDynastyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<MageObjectReference> creatures = new ArrayList<>();
        for (UUID uuid : getTargetPointer().getTargets(game, source)) {
            creatures.add(new MageObjectReference(uuid, game));
        }
        if (!creatures.isEmpty()) {
            game.addDelayedTriggeredAbility(new KangDynastyDelayedTriggeredAbility(creatures), source);
        }
        return true;
    }
}

class KangDynastyDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final List<MageObjectReference> creatures;

    KangDynastyDelayedTriggeredAbility(List<MageObjectReference> creatures) {
        super(new DrawCardSourceControllerEffect(1), Duration.UntilYourNextTurn, false, false);
        this.creatures = creatures;
        setTriggerPhrase("Until your next turn, whenever any of those creatures deals combat damage to a player, ");
    }

    private KangDynastyDelayedTriggeredAbility(final KangDynastyDelayedTriggeredAbility ability) {
        super(ability);
        this.creatures = new ArrayList<>(ability.creatures);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null) {
            return false;
        }
        return creatures.contains(new MageObjectReference(permanent.getId(), game));
    }

    @Override
    public KangDynastyDelayedTriggeredAbility copy() {
        return new KangDynastyDelayedTriggeredAbility(this);
    }
}
