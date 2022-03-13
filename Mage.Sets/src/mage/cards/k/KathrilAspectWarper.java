package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class KathrilAspectWarper extends CardImpl {

    public KathrilAspectWarper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Kathril, Aspect Warper enters the battlefield, put a flying counter on any creature you control if a creature card in your graveyard has flying. Repeat this process for first strike, double strike, deathtouch, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance. Then put a +1/+1 counter on Kathril for each counter put on a creature this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KathrilAspectWarperEffect()));
    }

    private KathrilAspectWarper(final KathrilAspectWarper card) {
        super(card);
    }

    @Override
    public KathrilAspectWarper copy() {
        return new KathrilAspectWarper(this);
    }
}

class KathrilAspectWarperEffect extends OneShotEffect {

    KathrilAspectWarperEffect() {
        super(Outcome.Benefit);
        staticText = "put a flying counter on any creature you control if a creature card in your graveyard has flying. " +
                "Repeat this process for first strike, double strike, deathtouch, hexproof, " +
                "indestructible, lifelink, menace, reach, trample, and vigilance. " +
                "Then put a +1/+1 counter on {this} for each counter put on a creature this way.";
    }

    private KathrilAspectWarperEffect(final KathrilAspectWarperEffect effect) {
        super(effect);
    }

    @Override
    public KathrilAspectWarperEffect copy() {
        return new KathrilAspectWarperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().countAll(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
        ) == 0) {
            return false;
        }
        Set<CounterType> counterSet = player
                .getGraveyard()
                .getCards(StaticFilters.FILTER_CARD_CREATURE, game)
                .stream()
                .map(Card::getAbilities)
                .flatMap(Collection::stream)
                .map(this::checkAbility)
                .collect(Collectors.toSet());
        if (counterSet == null || counterSet.size() == 0) {
            return false;
        }
        int countersAdded = 0;
        for (CounterType counterType : counterSet) {
            if (counterType == null) {
                continue;
            }
            FilterControlledPermanent filter
                    = new FilterControlledCreaturePermanent("creature to give a " + counterType + " counter");
            Target target = new TargetControlledPermanent(filter);
            target.setNotTarget(true);
            if (!player.choose(outcome, target, source, game)) {
                continue;
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            if (permanent.addCounters(counterType.createInstance(), source.getControllerId(), source, game)) {
                countersAdded++;
            }
        }
        if (countersAdded == 0) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return true;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(countersAdded), source.getControllerId(), source, game);
        return true;
    }

    private CounterType checkAbility(Ability ability) {
        if (ability instanceof FlyingAbility) {
            return CounterType.FLYING;
        }
        if (ability instanceof FirstStrikeAbility) {
            return CounterType.FIRST_STRIKE;
        }
        if (ability instanceof DoubleStrikeAbility) {
            return CounterType.DOUBLE_STRIKE;
        }
        if (ability instanceof DeathtouchAbility) {
            return CounterType.DEATHTOUCH;
        }
        if (ability instanceof HexproofBaseAbility) {
            return CounterType.HEXPROOF;
        }
        if (ability instanceof IndestructibleAbility) {
            return CounterType.INDESTRUCTIBLE;
        }
        if (ability instanceof LifelinkAbility) {
            return CounterType.LIFELINK;
        }
        if (ability instanceof MenaceAbility) {
            return CounterType.MENACE;
        }
        if (ability instanceof ReachAbility) {
            return CounterType.REACH;
        }
        if (ability instanceof TrampleAbility) {
            return CounterType.TRAMPLE;
        }
        if (ability instanceof VigilanceAbility) {
            return CounterType.VIGILANCE;
        }
        return null;
    }
}