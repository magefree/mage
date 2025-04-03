package mage.cards.k;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarakykGuardian extends CardImpl {

    public KarakykGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // This creature has hexproof if it hasn't dealt damage yet.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                KarakykGuardianCondition.instance, "{this} has hexproof if it hasn't dealt damage yet"
        )), new KarakykGuardianWatcher());
    }

    private KarakykGuardian(final KarakykGuardian card) {
        super(card);
    }

    @Override
    public KarakykGuardian copy() {
        return new KarakykGuardian(this);
    }
}

enum KarakykGuardianCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        KarakykGuardianWatcher watcher = game.getState().getWatcher(KarakykGuardianWatcher.class);
        return permanent != null && !watcher.getDamagers().contains(new MageObjectReference(permanent, game));
    }

    @Override
    public String toString() {
        return "{this} hasn't dealt damage yet";
    }

}

class KarakykGuardianWatcher extends Watcher {

    private final Set<MageObjectReference> damagers = new HashSet<>();

    public KarakykGuardianWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PERMANENT:
            case DAMAGED_PLAYER:
                break;
            default:
                return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            damagers.add(new MageObjectReference(permanent, game));
        }
    }

    public Set<MageObjectReference> getDamagers() {
        return damagers;
    }
}
