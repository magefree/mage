package mage.cards.p;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
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
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class PalladiaMorsTheRuiner extends CardImpl {

    public PalladiaMorsTheRuiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Palladia-Mors, the Ruiner has hexproof if it hasn't dealt damage yet.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                        PalladiaMorsTheRuinerCondition.instance,
                        "{this} has hexproof if it hasn't dealt damage yet"
                )
        ), new PalladiaMorsTheRuinerWatcher());
    }

    private PalladiaMorsTheRuiner(final PalladiaMorsTheRuiner card) {
        super(card);
    }

    @Override
    public PalladiaMorsTheRuiner copy() {
        return new PalladiaMorsTheRuiner(this);
    }
}

enum PalladiaMorsTheRuinerCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        PalladiaMorsTheRuinerWatcher watcher = game.getState().getWatcher(PalladiaMorsTheRuinerWatcher.class);
        return permanent != null && !watcher.getDamagers().contains(new MageObjectReference(permanent, game));
    }

    @Override
    public String toString() {
        return "{this} hasn't dealt damage yet";
    }

}

class PalladiaMorsTheRuinerWatcher extends Watcher {

    private final Set<MageObjectReference> damagers = new HashSet<>();

    public PalladiaMorsTheRuinerWatcher() {
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
