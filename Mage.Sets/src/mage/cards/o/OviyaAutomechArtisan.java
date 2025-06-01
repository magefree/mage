package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OviyaAutomechArtisan extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("each creature that's attacking one of your opponents");

    static {
        filter.add(OviyaAutomechArtisanPredicate.instance);
    }

    public OviyaAutomechArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Each creature that's attacking one of your opponents has trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // {G}, {T}: You may put a creature or Vehicle card from your hand onto the battlefield. If you put an artifact onto the battlefield this way, put two +1/+1 counters on it.
        Ability ability = new SimpleActivatedAbility(new OviyaAutomechArtisanEffect(), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private OviyaAutomechArtisan(final OviyaAutomechArtisan card) {
        super(card);
    }

    @Override
    public OviyaAutomechArtisan copy() {
        return new OviyaAutomechArtisan(this);
    }
}

enum OviyaAutomechArtisanPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return game
                .getOpponents(input.getPlayerId())
                .contains(game.getCombat().getDefenderId(input.getObject().getId()));
    }
}

class OviyaAutomechArtisanEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or Vehicle card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    OviyaAutomechArtisanEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature or Vehicle card from your hand onto the battlefield. " +
                "If you put an artifact onto the battlefield this way, put two +1/+1 counters on it";
    }

    private OviyaAutomechArtisanEffect(final OviyaAutomechArtisanEffect effect) {
        super(effect);
    }

    @Override
    public OviyaAutomechArtisanEffect copy() {
        return new OviyaAutomechArtisanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, filter);
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent != null && permanent.isArtifact(game)) {
            permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
        }
        return true;
    }
}
