package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterOpponent;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Balor extends CardImpl {

    private static final FilterPlayer filter0 = new FilterPlayer("a different player");
    private static final FilterPlayer filter1 = new FilterOpponent();
    private static final FilterPlayer filter2 = new FilterOpponent();
    private static final FilterPlayer filter3 = new FilterOpponent();
    private static final FilterPermanent filter4 = new FilterArtifactPermanent("a nontoken artifact");

    static {
        filter1.add(new AnotherTargetPredicate(1, true));
        filter2.add(new AnotherTargetPredicate(2, true));
        filter3.add(new AnotherTargetPredicate(3, true));
        filter4.add(TokenPredicate.FALSE);
    }

    public Balor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Balor attacks or dies, choose one or more. Each mode must target a different player.
        // • Target opponent draws three cards, then discards three cards at random.
        Ability ability = new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new DrawDiscardTargetEffect(
                        3, 3, true
                ), false, "Whenever {this} attacks or dies, ",
                new AttacksTriggeredAbility(null, false),
                new DiesSourceTriggeredAbility(null, false)
        );
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(3);
        ability.getModes().setMaxModesFilter(filter0);
        ability.addTarget(new TargetPlayer(filter1).setTargetTag(1).withChooseHint("to draw and discard"));

        // • Target opponent sacrifices a nontoken artifact.
        ability.addMode(new Mode(new SacrificeEffect(
                filter4, 1, "target opponent"
        )).addTarget(new TargetPlayer(filter2).setTargetTag(2).withChooseHint("to sacrifice an artifact")));

        // • Balor deals damage to target opponent equal to the number of cards in their hand.
        ability.addMode(new Mode(new BalorEffect()).addTarget(new TargetPlayer(filter3)
                .setTargetTag(3).withChooseHint("to deal damage")));
        this.addAbility(ability);
    }

    private Balor(final Balor card) {
        super(card);
    }

    @Override
    public Balor copy() {
        return new Balor(this);
    }
}

class BalorEffect extends OneShotEffect {

    BalorEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to target opponent equal to the number of cards in their hand";
    }

    private BalorEffect(final BalorEffect effect) {
        super(effect);
    }

    @Override
    public BalorEffect copy() {
        return new BalorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        return player != null
                && player.getHand().size() >= 1
                && player.damage(player.getHand().size(), source.getSourceId(), source, game) > 0;
    }
}
