package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InniazTheGaleForce extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Attacking creatures with flying");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("creatures you control with flying");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public InniazTheGaleForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{W/U}: Attacking creatures with flying get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostAllEffect(
                1, 1, Duration.EndOfTurn, filter, false
        ), new ManaCostsImpl("{2}{W/U}")));

        // Whenever three or more creatures you control with flying attack, each player gains control 
        // of a nonland permanent of your choice controlled by the player to their right.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new InniazTheGaleForceEffect(), 3, filter2
        ));
    }

    private InniazTheGaleForce(final InniazTheGaleForce card) {
        super(card);
    }

    @Override
    public InniazTheGaleForce copy() {
        return new InniazTheGaleForce(this);
    }
}

class InniazTheGaleForceEffect extends OneShotEffect {

    private static final class PlayerPair {

        private final Player leftPlayer;
        private final Player rightPlayer;
        private final FilterPermanent filter;
        private final TargetPermanent target;

        private PlayerPair(Player rightPlayer, Player leftPlayer) {
            this.leftPlayer = leftPlayer;
            this.rightPlayer = rightPlayer;
            this.filter = this.makeFilter();
            this.target = this.makeTarget();
        }

        private FilterPermanent makeFilter() {
            FilterPermanent filter = new FilterNonlandPermanent(
                    "nonland permanent controlled by " + rightPlayer.getName()
                            + " to give to " + leftPlayer.getName()
            );
            filter.add(new ControllerIdPredicate(rightPlayer.getId()));
            return filter;
        }

        private TargetPermanent makeTarget() {
            return new TargetPermanent(1, 1, this.filter, true);
        }

        private void chooseTargets(Player controller, Game game, Ability source) {
            if (game.getBattlefield().count(this.filter, source.getControllerId(), source, game) > 0) {
                controller.choose(Outcome.Neutral, this.target, source, game);
            }
        }

        private void createEffect(Game game, Ability source) {
            if (this.target.getFirstTarget() == null) {
                return;
            }
            game.addEffect(new GainControlTargetEffect(
                    Duration.Custom, true, leftPlayer.getId()
            ).setTargetPointer(new FixedTarget(target.getFirstTarget(), game)), source);
        }
    }

    InniazTheGaleForceEffect() {
        super(Outcome.Benefit);
        staticText = "each player gains control of a nonland permanent of your choice controlled by the player to their right.";
    }

    private InniazTheGaleForceEffect(final InniazTheGaleForceEffect effect) {
        super(effect);
    }

    @Override
    public InniazTheGaleForceEffect copy() {
        return new InniazTheGaleForceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        List<PlayerPair> playerPairList = new ArrayList<>();
        for (int i = 0; i < playerList.size() - 1; i++) {
            playerPairList.add(new PlayerPair(
                    game.getPlayer(playerList.get(i)),
                    game.getPlayer(playerList.get(i + 1))
            ));
        }
        playerPairList.add(new PlayerPair(
                game.getPlayer(playerList.get(playerList.size() - 1)),
                game.getPlayer(playerList.get(0))
        ));
        for (PlayerPair playerPair : playerPairList) {
            playerPair.chooseTargets(controller, game, source);
        }
        for (PlayerPair playerPair : playerPairList) {
            playerPair.createEffect(game, source);
        }
        return true;
    }
}
