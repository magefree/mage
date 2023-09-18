
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
        filter.add(new TelimTorsEdictPredicate());
    }

    public TelimTorsEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Exile target permanent you own or control.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
    }

    private TelimTorsEdict(final TelimTorsEdict card) {
        super(card);
    }

    @Override
    public TelimTorsEdict copy() {
        return new TelimTorsEdict(this);
    }
}

class TelimTorsEdictPredicate implements ObjectSourcePlayerPredicate<Permanent> {

    public TelimTorsEdictPredicate() {
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getObject();
        UUID playerId = input.getPlayerId();
        if (permanent.isControlledBy(playerId) || permanent.isOwnedBy(playerId)) {
            return true;
        }
        return false;
    }
}
