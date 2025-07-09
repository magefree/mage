package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfernalDenizen extends CardImpl {

    public InfernalDenizen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // At the beginning of your upkeep, sacrifice two Swamps. If you can't, tap Infernal Denizen, and an opponent may gain control of a creature you control of their choice for as long as Infernal Denizen remains on the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InfernalDenizenEffect()));

        // {tap}: Gain control of target creature for as long as Infernal Denizen remains on the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new GainControlTargetEffect(Duration.UntilSourceLeavesBattlefield, true)
                        .setText("gain control of target creature for as long as {this} remains on the battlefield"),
                new TapSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private InfernalDenizen(final InfernalDenizen card) {
        super(card);
    }

    @Override
    public InfernalDenizen copy() {
        return new InfernalDenizen(this);
    }
}

class InfernalDenizenEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP);

    InfernalDenizenEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice two Swamps. If you can't, tap {this}, "
                + "and an opponent may gain control of a creature you control of their choice "
                + "for as long as {this} remains on the battlefield";
    }

    private InfernalDenizenEffect(final InfernalDenizenEffect effect) {
        super(effect);
    }

    @Override
    public InfernalDenizenEffect copy() {
        return new InfernalDenizenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = new SacrificeTargetCost(filter);
        if (cost.canPay(source, source, source.getControllerId(), game)
                && cost.pay(source, game, source, source.getControllerId(), true)) {
            return true;
        }
        Permanent creature = source.getSourcePermanentIfItStillExists(game);
        if (creature == null) {
            return false;
        }
        creature.tap(source, game);
        TargetPlayer targetPlayer = new TargetOpponent(true);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return true;
        }
        player.choose(outcome, targetPlayer, source, game);
        Player opponent = game.getPlayer(targetPlayer.getFirstTarget());
        if (opponent == null) {
            return true;
        }
        FilterPermanent filterPermanent = new FilterCreaturePermanent("creature controlled by " + player.getName());
        filterPermanent.add(new ControllerIdPredicate(player.getId()));
        TargetPermanent target = new TargetPermanent(0, 1, filterPermanent, true);
        opponent.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            game.addEffect(new GainControlTargetEffect(
                    Duration.UntilSourceLeavesBattlefield, true, opponent.getId()
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
