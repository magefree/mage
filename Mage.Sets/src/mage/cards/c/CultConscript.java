package mage.cards.c;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author weirddan455
 */
public final class CultConscript extends CardImpl {

    public CultConscript(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Cult Conscript enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {1}{B}: Return Cult Conscript from your graveyard to the battlefield. Activate only if a non-Skeleton creature died under your control this turn.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(false, false),
                new ManaCostsImpl<>("{1}{B}"),
                CultConscriptCondition.instance
        ), new CultConscriptWatcher());
    }

    private CultConscript(final CultConscript card) {
        super(card);
    }

    @Override
    public CultConscript copy() {
        return new CultConscript(this);
    }
}

enum CultConscriptCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CultConscriptWatcher watcher = game.getState().getWatcher(CultConscriptWatcher.class);
        return watcher != null && watcher.nonSkeletonDied(source.getControllerId());
    }

    @Override
    public String toString() {
        return "a non-Skeleton creature died under your control this turn";
    }
}

class CultConscriptWatcher extends Watcher {

    private final HashSet<UUID> playerSet = new HashSet<>();

    public CultConscriptWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()) {
                Permanent permanent = zEvent.getTarget();
                if (permanent != null && permanent.isCreature(game) && !permanent.hasSubtype(SubType.SKELETON, game)) {
                    playerSet.add(permanent.getControllerId());
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    public boolean nonSkeletonDied(UUID playerId) {
        return playerSet.contains(playerId);
    }
}
