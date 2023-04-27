package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class MasterOfTheWildHunt extends CardImpl {

    private static WolfToken wolfToken = new WolfToken();

    public MasterOfTheWildHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, create a 2/2 green Wolf creature token.
        this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new CreateTokenEffect(wolfToken)));

        // {T}: Tap all untapped Wolf creatures you control. Each Wolf tapped this way deals damage equal to its power to target creature. That creature deals damage equal to its power divided as its controller chooses among any number of those Wolves.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MasterOfTheWildHuntEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MasterOfTheWildHunt(final MasterOfTheWildHunt card) {
        super(card);
    }

    @Override
    public MasterOfTheWildHunt copy() {
        return new MasterOfTheWildHunt(this);
    }
}

class MasterOfTheWildHuntEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.WOLF.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public MasterOfTheWildHuntEffect() {
        super(Outcome.Damage);
        staticText = "Tap all untapped Wolf creatures you control. Each Wolf tapped this way deals damage equal to its power to target creature. That creature deals damage equal to its power divided as its controller chooses among any number of those Wolves";
    }

    public MasterOfTheWildHuntEffect(final MasterOfTheWildHuntEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfTheWildHuntEffect copy() {
        return new MasterOfTheWildHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> wolves = new ArrayList<>();
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null && game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                permanent.tap(source, game);
                target.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
                wolves.add(permanent.getId());
            }
            Player player = game.getPlayer(target.getControllerId());
            if (player != null) {
                player.assignDamage(target.getPower().getValue(), wolves, "Wolf", target.getId(), source, game);
                return true;
            }
        }
        return false;
    }

}
