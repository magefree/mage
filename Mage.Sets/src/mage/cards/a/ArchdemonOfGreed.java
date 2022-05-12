package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class ArchdemonOfGreed extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Human");

    static {
        filter.add(SubType.HUMAN.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ArchdemonOfGreed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.DEMON);
        this.color.setBlack(true);

        this.nightCard = true;

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, sacrifice a Human. If you can't, tap Archdemon of Greed and it deals 9 damage to you.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new ArchdemonOfGreedEffect(), false));
    }

    private ArchdemonOfGreed(final ArchdemonOfGreed card) {
        super(card);
    }

    @Override
    public ArchdemonOfGreed copy() {
        return new ArchdemonOfGreed(this);
    }

    static class ArchdemonOfGreedEffect extends OneShotEffect {

        public ArchdemonOfGreedEffect() {
            super(Outcome.Damage);
            this.staticText = "Sacrifice a Human. If you can't, tap {this} and it deals 9 damage to you.";
        }

        public ArchdemonOfGreedEffect(final ArchdemonOfGreedEffect effect) {
            super(effect);
        }

        @Override
        public ArchdemonOfGreedEffect copy() {
            return new ArchdemonOfGreedEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());

            if (permanent != null) {
                // create cost for sacrificing a human
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, false);
                    // if they can pay the cost, then they must pay
                    if (target.canChoose(player.getId(), source, game)) {
                        player.choose(Outcome.Sacrifice, target, source, game);
                        Permanent humanSacrifice = game.getPermanent(target.getFirstTarget());
                        if (humanSacrifice != null) {
                            // sacrifice the chosen card
                            return humanSacrifice.sacrifice(source, game);
                        }
                    } else {
                        permanent.tap(source, game);
                        player.damage(9, source.getSourceId(), source, game);
                    }
                }
                return true;
            }
            return false;
        }
    }
}
