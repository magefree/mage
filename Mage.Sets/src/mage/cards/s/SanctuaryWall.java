package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanctuaryWall extends CardImpl {

    public SanctuaryWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}{W}, {T}: Tap target creature. You may put a stun counter on it. If you do, put a stun counter on Sanctuary Wall.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new SanctuaryWallEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SanctuaryWall(final SanctuaryWall card) {
        super(card);
    }

    @Override
    public SanctuaryWall copy() {
        return new SanctuaryWall(this);
    }
}

class SanctuaryWallEffect extends OneShotEffect {

    SanctuaryWallEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a stun counter on it. If you do, put a stun counter on {this}";
    }

    private SanctuaryWallEffect(final SanctuaryWallEffect effect) {
        super(effect);
    }

    @Override
    public SanctuaryWallEffect copy() {
        return new SanctuaryWallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null
                || !player.chooseUse(outcome, "Put a stun counter on " + permanent.getIdName() + '?', source, game)
                || !permanent.addCounters(CounterType.STUN.createInstance(), source, game)) {
            return false;
        }
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(p -> p.addCounters(CounterType.STUN.createInstance(), source, game));
        return true;
    }
}
