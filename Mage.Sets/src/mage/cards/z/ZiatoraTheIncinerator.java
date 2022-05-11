package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZiatoraTheIncinerator extends CardImpl {

    public ZiatoraTheIncinerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, you may sacrifice another creature. When you do, Ziatora, the Incinerator deals damage equal to that creature's power to any target and you create three Treasure tokens.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ZiatoraTheIncineratorEffect(), TargetController.YOU, false
        ));
    }

    private ZiatoraTheIncinerator(final ZiatoraTheIncinerator card) {
        super(card);
    }

    @Override
    public ZiatoraTheIncinerator copy() {
        return new ZiatoraTheIncinerator(this);
    }
}

class ZiatoraTheIncineratorEffect extends OneShotEffect {

    ZiatoraTheIncineratorEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another creature. When you do, {this} deals damage " +
                "equal to that creature's power to any target and you create three Treasure tokens";
    }

    private ZiatoraTheIncineratorEffect(final ZiatoraTheIncineratorEffect effect) {
        super(effect);
    }

    @Override
    public ZiatoraTheIncineratorEffect copy() {
        return new ZiatoraTheIncineratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        );
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(permanent.getPower().getValue()), false
        );
        ability.addEffect(new CreateTokenEffect(new TreasureToken(), 3).concatBy("and"));
        ability.addTarget(new TargetAnyTarget());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
