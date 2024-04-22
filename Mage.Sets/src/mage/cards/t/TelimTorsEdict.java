package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class TelimTorsEdict extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent you own or control");

    static {
        filter.add(TelimTorsEdictPredicate.instance);
    }

    public TelimTorsEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Exile target permanent you own or control.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                new DrawCardSourceControllerEffect(1)), false).concatBy("<br>"));
    }

    private TelimTorsEdict(final TelimTorsEdict card) {
        super(card);
    }

    @Override
    public TelimTorsEdict copy() {
        return new TelimTorsEdict(this);
    }
}

enum TelimTorsEdictPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getObject();
        UUID playerId = input.getPlayerId();
        return permanent.isControlledBy(playerId) || permanent.isOwnedBy(playerId);
    }
}
