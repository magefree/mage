package mage.cards.a;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AscentOfTheWorthy extends CardImpl {

    public AscentOfTheWorthy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Choose a creature you control. Until your next turn, all damage that would be dealt to creatures you control is dealt to that creature instead.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, new AscentOfTheWorthyEffect()
        );

        // III — Return target creature card from your graveyard to the battlefield with a flying counter on it. That creature is an Angel Warrior in addition to its other types.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new AscentOfTheWorthyReturnEffect(),
                new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        );

        this.addAbility(sagaAbility);
    }

    private AscentOfTheWorthy(final AscentOfTheWorthy card) {
        super(card);
    }

    @Override
    public AscentOfTheWorthy copy() {
        return new AscentOfTheWorthy(this);
    }
}

class AscentOfTheWorthyEffect extends OneShotEffect {

    AscentOfTheWorthyEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature you control. Until your next turn, " +
                "all damage that would be dealt to creatures you control is dealt to that creature instead";
    }

    private AscentOfTheWorthyEffect(final AscentOfTheWorthyEffect effect) {
        super(effect);
    }

    @Override
    public AscentOfTheWorthyEffect copy() {
        return new AscentOfTheWorthyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.informPlayers(player.getName() + " chooses to have all damage redirected to " + permanent.getIdName());
        game.addEffect(new AscentOfTheWorthyRedirectEffect(new MageObjectReference(permanent, game)), source);
        return false;
    }
}

class AscentOfTheWorthyRedirectEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    AscentOfTheWorthyRedirectEffect(MageObjectReference mor) {
        super(Duration.UntilYourNextTurn, Outcome.RedirectDamage);
        this.mor = mor;
    }

    private AscentOfTheWorthyRedirectEffect(final AscentOfTheWorthyRedirectEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent permanent = mor.getPermanent(game);
        if (permanent != null) {
            permanent.damage(damageEvent.getAmount(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && permanent.isCreature(game) && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public AscentOfTheWorthyRedirectEffect copy() {
        return new AscentOfTheWorthyRedirectEffect(this);
    }
}

class AscentOfTheWorthyReturnEffect extends OneShotEffect {

    AscentOfTheWorthyReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield with a flying counter on it. " +
                "That creature is an Angel Warrior in addition to its other types";
    }

    private AscentOfTheWorthyReturnEffect(final AscentOfTheWorthyReturnEffect effect) {
        super(effect);
    }

    @Override
    public AscentOfTheWorthyReturnEffect copy() {
        return new AscentOfTheWorthyReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.FLYING.createInstance(), source.getControllerId(), source, game);
        game.addEffect(new AddCardSubTypeTargetEffect(
                SubType.ANGEL, Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addEffect(new AddCardSubTypeTargetEffect(
                SubType.WARRIOR, Duration.Custom
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
