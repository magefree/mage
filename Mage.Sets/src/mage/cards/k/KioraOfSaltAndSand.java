package mage.cards.k;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Leviathan88Token;
import mage.game.stack.StackAbility;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

/**
 * @author muz
 */
public final class KioraOfSaltAndSand extends CardImpl {

    public KioraOfSaltAndSand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you attack, if you've activated a loyalty ability this turn, untap target attacking creature. It can't be blocked this turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new UntapTargetEffect(), 1)
                .withInterveningIf(KioraOfSaltAndSandCondition.instance);
        ability.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn).setText("It can't be blocked this turn"));
        ability.addTarget(new TargetPermanent(new FilterAttackingCreature()));
        this.addAbility(ability, new KioraOfSaltAndSandWatcher());

        // Planeswalkers you control have "[-8]: Create an 8/8 blue Leviathan creature token with hexproof."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            new LoyaltyAbility(new CreateTokenEffect(new Leviathan88Token()), -8),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"-8: Create an 8/8 blue Leviathan creature token with hexproof.\"")));
    }

    private KioraOfSaltAndSand(final KioraOfSaltAndSand card) {
        super(card);
    }

    @Override
    public KioraOfSaltAndSand copy() {
        return new KioraOfSaltAndSand(this);
    }
}

enum KioraOfSaltAndSandCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        KioraOfSaltAndSandWatcher watcher = game.getState().getWatcher(KioraOfSaltAndSandWatcher.class);
        return watcher != null && watcher.activatedPlayers.contains(source.getControllerId());
    }

    @Override
    public String toString() {
        return "you've activated a loyalty ability this turn";
    }
}

class KioraOfSaltAndSandWatcher extends Watcher {

    final Set<UUID> activatedPlayers = new HashSet<>();

    KioraOfSaltAndSandWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        StackAbility ability = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (ability != null && ability.getStackAbility() instanceof LoyaltyAbility) {
            activatedPlayers.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        activatedPlayers.clear();
    }
}
