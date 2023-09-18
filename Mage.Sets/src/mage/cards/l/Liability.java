package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Liability extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public Liability(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // Whenever a nontoken permanent is put into a player's graveyard from the battlefield, that player loses 1 life.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new LiabilityEffect(), false, filter, true));
    }

    private Liability(final Liability card) {
        super(card);
    }

    @Override
    public Liability copy() {
        return new Liability(this);
    }
}

class LiabilityEffect extends OneShotEffect {

    public LiabilityEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player loses 1 life.";
    }

    private LiabilityEffect(final LiabilityEffect effect) {
        super(effect);
    }

    @Override
    public LiabilityEffect copy() {
        return new LiabilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if (controller != null) {
                controller.loseLife(1, game, source, false);
                return true;
            }
        }
        return false;
    }
}
