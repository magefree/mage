package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MerfolkWhiteBlueToken;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class WanderwineFarewell extends CardImpl {

    public WanderwineFarewell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.SORCERY}, "{5}{U}{U}");

        this.subtype.add(SubType.MERFOLK);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Return one or two target nonland permanents to their owners' hands. Then if you control a Merfolk, create a 1/1 white and blue Merfolk creature token for each permanent returned to its owner's hand this way.
        this.getSpellAbility().addEffect(new WanderwineFarewellEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(1, 2));
        this.getSpellAbility().addHint(WanderwineFarewellEffect.getHint());
    }

    private WanderwineFarewell(final WanderwineFarewell card) {
        super(card);
    }

    @Override
    public WanderwineFarewell copy() {
        return new WanderwineFarewell(this);
    }
}

class WanderwineFarewellEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MERFOLK);
    private static final Hint hint = new ConditionHint(
            new PermanentsOnTheBattlefieldCondition(filter), "You control a Merfolk"
    );

    public static Hint getHint() {
        return hint;
    }

    WanderwineFarewellEffect() {
        super(Outcome.Benefit);
        staticText = "return one or two target nonland permanents to their owners' hands. " +
                "Then if you control a Merfolk, create a 1/1 white and blue Merfolk creature token " +
                "for each permanent returned to its owner's hand this way";
    }

    private WanderwineFarewellEffect(final WanderwineFarewellEffect effect) {
        super(effect);
    }

    @Override
    public WanderwineFarewellEffect copy() {
        return new WanderwineFarewellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        int count = permanents.size();
        if (count < 1) {
            return false;
        }
        player.moveCards(permanents, Zone.HAND, source, game);
        if (game.getBattlefield().contains(filter, source, game, 1)) {
            new MerfolkWhiteBlueToken().putOntoBattlefield(count, game, source);
        }
        return true;
    }
}
