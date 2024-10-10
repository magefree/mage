package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class DeputyOfDetention extends CardImpl {

    public DeputyOfDetention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Deputy of Detention enters the battlefield, exile target nonland permanent an opponent controls and all other nonland permanents that player controls with the same name as that permanent until Deputy of Detention leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DeputyOfDetentionExileEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);
    }

    private DeputyOfDetention(final DeputyOfDetention card) {
        super(card);
    }

    @Override
    public DeputyOfDetention copy() {
        return new DeputyOfDetention(this);
    }
}

class DeputyOfDetentionExileEffect extends OneShotEffect {

    DeputyOfDetentionExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target nonland permanent an opponent controls " +
                "and all other nonland permanents that player controls " +
                "with the same name as that permanent until {this} leaves the battlefield";
    }

    private DeputyOfDetentionExileEffect(final DeputyOfDetentionExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent targeted = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || controller == null || targeted == null) {
            return false;
        }
        Set<Card> set = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND,
                        targeted.getControllerId(), source, game
                )
                .stream()
                .filter(p -> p.sharesName(targeted, game))
                .collect(Collectors.toSet());
        set.add(targeted);
        return new ExileUntilSourceLeavesEffect().setTargetPointer(new FixedTargets(set, game)).apply(game, source);
    }

    @Override
    public DeputyOfDetentionExileEffect copy() {
        return new DeputyOfDetentionExileEffect(this);
    }
}
