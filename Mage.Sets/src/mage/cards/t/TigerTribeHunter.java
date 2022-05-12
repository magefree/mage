package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.PackTacticsAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TigerTribeHunter extends CardImpl {

    public TigerTribeHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Pack tactics — Whenever Tiger-Tribe Hunter attacks, if you attacked with creatures with total power 6 or greater this combat, you may sacrifice another creature. When you do, Tiger-Tribe Hunter deals damage equal to the sacrificed creature's power to target creature.
        this.addAbility(new PackTacticsAbility(new TigerTribeHunterEffect()));
    }

    private TigerTribeHunter(final TigerTribeHunter card) {
        super(card);
    }

    @Override
    public TigerTribeHunter copy() {
        return new TigerTribeHunter(this);
    }
}

class TigerTribeHunterEffect extends OneShotEffect {

    TigerTribeHunterEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature. When you do, " +
                "{this} deals damage equal to the sacrificed creature's power to target creature";
    }

    private TigerTribeHunterEffect(final TigerTribeHunterEffect effect) {
        super(effect);
    }

    @Override
    public TigerTribeHunterEffect copy() {
        return new TigerTribeHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        );
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int power = Math.max(permanent.getPower().getValue(), 0);
        if (!permanent.sacrifice(source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(power), false,
                "{this} deals damage equal to the sacrificed creature's power to target creature."
        );
        ability.addTarget(new TargetCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
