package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class MikeyAndMonaMutantSitters extends CardImpl {

    private static final FilterPlayer filter0 = new FilterPlayer("a different player");
    private static final FilterPlayer filter1 = new FilterPlayer();
    private static final FilterPlayer filter2 = new FilterPlayer();

    static {
        filter1.add(new AnotherTargetPredicate(1, true));
        filter2.add(new AnotherTargetPredicate(2, true));
    }

    public MikeyAndMonaMutantSitters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mikey & Mona enter, choose one or both. Each mode must target a different player.
        // * Target player chooses a creature they control and puts two +1/+1 counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MikeyAndMonaCountersEffect());
        ability.addTarget(new TargetPlayer(filter1).withChooseHint("to put two +1/+1 counters on a creature"));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(2);
        ability.getModes().setLimitUsageByOnce(false);
        ability.getModes().setMaxModesFilter(filter0);

        // * Target player returns a creature or land card from their graveyard to their hand.
        ability.addMode(new Mode(new MikeyAndMonaGraveyardEffect())
            .addTarget(new TargetPlayer(filter2).withChooseHint("to return a card from their graveyard to their hand")));
        this.addAbility(ability);
    }

    private MikeyAndMonaMutantSitters(final MikeyAndMonaMutantSitters card) {
        super(card);
    }

    @Override
    public MikeyAndMonaMutantSitters copy() {
        return new MikeyAndMonaMutantSitters(this);
    }
}

class MikeyAndMonaCountersEffect extends OneShotEffect {

    MikeyAndMonaCountersEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player chooses a creature they control and puts two +1/+1 counters on it.";
    }

    private MikeyAndMonaCountersEffect(final MikeyAndMonaCountersEffect effect) {
        super(effect);
    }

    @Override
    public MikeyAndMonaCountersEffect copy() {
        return new MikeyAndMonaCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");
        filter.add(new ControllerIdPredicate(player.getId()));
        Target target = new TargetPermanent(1, 1, filter, true);
        if (player.chooseTarget(Outcome.DestroyPermanent, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
            }
        }
        return true;
    }
}

class MikeyAndMonaGraveyardEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or land card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    MikeyAndMonaGraveyardEffect() {
        super(Outcome.Benefit);
        staticText = "target player returns a creature or land card from their graveyard to their hand";
    }

    private MikeyAndMonaGraveyardEffect(final MikeyAndMonaGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public MikeyAndMonaGraveyardEffect copy() {
        return new MikeyAndMonaGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || player.getGraveyard().count(filter, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(filter);
        player.choose(Outcome.ReturnToHand, player.getGraveyard(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.HAND, source, game);
    }
}
