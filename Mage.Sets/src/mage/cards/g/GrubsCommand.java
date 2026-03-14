package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrubsCommand extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN);

    public GrubsCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.SORCERY}, "{3}{B}{R}");

        this.subtype.add(SubType.GOBLIN);

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Create a token that's a copy of target Goblin you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Creatures target player controls get +1/+1 and gain haste until end of turn.
        this.getSpellAbility().addMode(new Mode(new GrubsCommandBoostEffect())
                .addTarget(new TargetPlayer().withChooseHint("to give +1/+1 and haste")));

        // * Destroy target artifact or creature.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)
                        .withChooseHint("to destroy")));

        // * Target player mills five cards, then puts each Goblin card milled this way into their hand.
        this.getSpellAbility().addMode(new Mode(new GrubsCommandMillEffect())
                .addTarget(new TargetPlayer()));
    }

    private GrubsCommand(final GrubsCommand card) {
        super(card);
    }

    @Override
    public GrubsCommand copy() {
        return new GrubsCommand(this);
    }
}

class GrubsCommandBoostEffect extends OneShotEffect {

    GrubsCommandBoostEffect() {
        super(Outcome.Benefit);
        staticText = "creatures target player controls get +1/+1 and gain haste until end of turn";
    }

    private GrubsCommandBoostEffect(final GrubsCommandBoostEffect effect) {
        super(effect);
    }

    @Override
    public GrubsCommandBoostEffect copy() {
        return new GrubsCommandBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(getTargetPointer().getFirst(game, source)));
        game.addEffect(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), source);
        game.addEffect(new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter), source);
        return true;
    }
}

class GrubsCommandMillEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard(SubType.GOBLIN);

    GrubsCommandMillEffect() {
        super(Outcome.Benefit);
        staticText = "target player mills five cards, then puts each Goblin card milled this way into their hand";
    }

    private GrubsCommandMillEffect(final GrubsCommandMillEffect effect) {
        super(effect);
    }

    @Override
    public GrubsCommandMillEffect copy() {
        return new GrubsCommandMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || !player.getLibrary().hasCards()) {
            return false;
        }
        player.moveCards(
                player.millCards(5, source, game)
                        .getCards(filter, game),
                Zone.HAND, source, game
        );
        return true;
    }
}
