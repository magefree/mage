package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AddCounterNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.common.DamagedByControlledWatcher;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class KumanoFacesKakkazan extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public KumanoFacesKakkazan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{R}",
                "Etching of Kumano",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN}, "R");

        // Kumano Faces Kakkazan
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Kumano Faces Kakkazan deals 1 damage to each opponent and each planeswalker they control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_I,
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                new DamageAllEffect(1, filter).setText("and each planeswalker they control")
        );

        // II — When you cast your next creature spell this turn, that creature enters the battlefield with an additional +1/+1 counter on it.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_II,
                new CreateDelayedTriggeredAbilityEffect(new AddCounterNextSpellDelayedTriggeredAbility())
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Etching of Kumano
        this.getRightHalfCard().setPT(2, 2);

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // If a creature dealt damage this turn by a source you controlled would die, exile it instead.
        Ability ability = new SimpleStaticAbility(new EtchingOfKumanoReplacementEffect());
        ability.addWatcher(new DamagedByControlledWatcher());
        this.getRightHalfCard().addAbility(ability);
    }

    private KumanoFacesKakkazan(final KumanoFacesKakkazan card) {
        super(card);
    }

    @Override
    public KumanoFacesKakkazan copy() {
        return new KumanoFacesKakkazan(this);
    }
}

class EtchingOfKumanoReplacementEffect extends ReplacementEffectImpl {

    EtchingOfKumanoReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        this.staticText = "If a creature dealt damage this turn by a source you controlled would die, exile it instead";
    }

    private EtchingOfKumanoReplacementEffect(final EtchingOfKumanoReplacementEffect effect) {
        super(effect);
    }

    @Override
    public EtchingOfKumanoReplacementEffect copy() {
        return new EtchingOfKumanoReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (zce.isDiesEvent()) {
            DamagedByControlledWatcher watcher = game.getState().getWatcher(DamagedByControlledWatcher.class, source.getControllerId());
            if (watcher != null) {
                return watcher.wasDamaged(zce.getTarget(), game);
            }
        }
        return false;
    }
}
