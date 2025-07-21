package mage.cards.t;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class TemporaryInsanity extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power less than the number of cards in your graveyard");

    public TemporaryInsanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Untap target creature with power less than the number of cards in your graveyard
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // and gain control of it until end of turn.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn).setText("and gain control of it until end of turn"));

        // That creature gains haste until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("That creature gains haste until end of turn."));
    }

    private TemporaryInsanity(final TemporaryInsanity card) {
        super(card);
    }

    @Override
    public TemporaryInsanity copy() {
        return new TemporaryInsanity(this);
    }
}

enum TemporaryInsanityPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getPlayerId())
                .map(game::getPlayer)
                .map(Player::getGraveyard)
                .map(HashSet::size)
                .filter(x -> input.getObject().getPower().getValue() < x)
                .isPresent();
    }
}
