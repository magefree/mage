package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.common.TargetPermanentAmount;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AwakenTheMaelstrom extends CardImpl {

    public AwakenTheMaelstrom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.nightCard = true;

        // Awaken the Maelstrom is all colors.
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setRed(true);
        this.color.setGreen(true);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this} is all colors")));

        // Target player draws two cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer().withChooseHint("to draw two cards"));

        // You may put an artifact card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_ARTIFACT_AN));

        // Create a token that's a copy of a permanent you control.
        // Distribute three +1/+1 counters among one, two, or three creatures you control.
        this.getSpellAbility().addEffect(new AwakenTheMaelstromEffect());

        // Destroy target permanent an opponent controls.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT).withChooseHint("to destroy"));
    }

    private AwakenTheMaelstrom(final AwakenTheMaelstrom card) {
        super(card);
    }

    @Override
    public AwakenTheMaelstrom copy() {
        return new AwakenTheMaelstrom(this);
    }
}

class AwakenTheMaelstromEffect extends OneShotEffect {

    AwakenTheMaelstromEffect() {
        super(Outcome.Benefit);
        staticText = "Create a token that's a copy of a permanent you control. " +
                "Distribute three +1/+1 counters among one, two, or three creatures you control.";
    }

    private AwakenTheMaelstromEffect(final AwakenTheMaelstromEffect effect) {
        super(effect);
    }

    @Override
    public AwakenTheMaelstromEffect copy() {
        return new AwakenTheMaelstromEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        makeToken(player, game, source);
        distributeCounters(player, game, source);
        return true;
    }

    private void makeToken(Player player, Game game, Ability source) {
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        target.withChooseHint("to copy");
        if (!target.canChoose(player.getId(), source, game)) {
            return;
        }
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent != null) {
            new CreateTokenCopyTargetEffect().setSavedPermanent(permanent).apply(game, source);
        }
    }

    private void distributeCounters(Player player, Game game, Ability source) {
        if (game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game) < 1) {
            return;
        }
        TargetPermanentAmount target = new TargetCreaturePermanentAmount(3);
        target.setNotTarget(true);
        target.withChooseHint("to distribute counters");
        player.choose(outcome, target, source, game);
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(target.getTargetAmount(targetId)), source, game);
            }
        }
    }
}
