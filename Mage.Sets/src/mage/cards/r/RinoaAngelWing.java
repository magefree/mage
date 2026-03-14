package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DiesOneOrMoreTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RinoaAngelWing extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("attacking creatures you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public RinoaAngelWing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, creatures you control with flying get +1/+1 and gain vigilance until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn, StaticFilters.FILTER_CREATURE_FLYING
        ).setText("creatures you control with flying get +1/+1"));
        ability.addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CREATURE_FLYING
        ).setText("and gain vigilance until end of turn"));
        this.addAbility(ability);

        // Whenever one or more attacking creatures you control die, you may return one of them to the battlefield tapped under its owner's control with a flying counter on it. Do this only once each turn.
        this.addAbility(new DiesOneOrMoreTriggeredAbility(
                new RinoaAngelWingEffect(), filter, true, true
        ).setDoOnlyOnceEachTurn(true));
    }

    private RinoaAngelWing(final RinoaAngelWing card) {
        super(card);
    }

    @Override
    public RinoaAngelWing copy() {
        return new RinoaAngelWing(this);
    }
}

class RinoaAngelWingEffect extends OneShotEffect {

    RinoaAngelWingEffect() {
        super(Outcome.Benefit);
        staticText = "return one of them to the battlefield tapped " +
                "under its owner's control with a flying counter on it";
    }

    private RinoaAngelWingEffect(final RinoaAngelWingEffect effect) {
        super(effect);
    }

    @Override
    public RinoaAngelWingEffect copy() {
        return new RinoaAngelWingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        TargetCard target = new TargetCard(0, 1, Zone.ALL, StaticFilters.FILTER_CARD);
        target.withNotTarget(true);
        player.choose(Outcome.PutCreatureInPlay, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            game.setEnterWithCounters(card.getId(), new Counters(CounterType.FLYING.createInstance()));
            player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            if (CardUtil.getPermanentFromCardPutToBattlefield(card, game) != null) {
                return true;
            }
        }
        TriggeredAbility.clearDidThisTurn(source, game);
        return false;
    }
}
