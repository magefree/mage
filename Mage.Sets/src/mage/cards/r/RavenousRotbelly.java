package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenousRotbelly extends CardImpl {

    public RavenousRotbelly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Ravenous Rotbelly enters the battlefield, you may sacrifice up to three Zombies. When you sacrifice one or more Zombies this way, each opponent sacrifices that many creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RavenousRotbellyEffect()));
    }

    private RavenousRotbelly(final RavenousRotbelly card) {
        super(card);
    }

    @Override
    public RavenousRotbelly copy() {
        return new RavenousRotbelly(this);
    }
}

class RavenousRotbellyEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ZOMBIE, "Zombies you control");

    RavenousRotbellyEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice up to three Zombies. When you sacrifice " +
                "one or more Zombies this way, each opponent sacrifices that many creatures";
    }

    private RavenousRotbellyEffect(final RavenousRotbellyEffect effect) {
        super(effect);
    }

    @Override
    public RavenousRotbellyEffect copy() {
        return new RavenousRotbellyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 3, filter, true);
        player.choose(outcome, target, source, game);
        int amount = 0;
        for (UUID permanentId : target.getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                amount++;
            }
        }
        if (amount < 1) {
            return false;
        }
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new SacrificeOpponentsEffect(amount, StaticFilters.FILTER_PERMANENT_CREATURES),
                false, "each opponent sacrifices that many creatures"
        ), source);
        return true;
    }
}
