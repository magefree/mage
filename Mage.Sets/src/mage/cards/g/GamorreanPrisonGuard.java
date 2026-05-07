package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class GamorreanPrisonGuard extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public GamorreanPrisonGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GAMORREAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a creature enters the battlefield under an opponent's control, Gamorrean Prison Guard fights that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new GamorreanPrisonGuardEffect(), filter, false, SetTargetPointer.PERMANENT)
                .setTriggerPhrase("Whenever a creature enters under an opponent's control, "));

    }

    private GamorreanPrisonGuard(final GamorreanPrisonGuard card) {
        super(card);
    }

    @Override
    public GamorreanPrisonGuard copy() {
        return new GamorreanPrisonGuard(this);
    }
}

class GamorreanPrisonGuardEffect extends OneShotEffect {

    GamorreanPrisonGuardEffect() {
        super(Outcome.Detriment);
        setText("{this} fights that creature");
    }

    private GamorreanPrisonGuardEffect(final GamorreanPrisonGuardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent thisCreature = source.getSourcePermanentIfItStillExists(game);
        Permanent opponentCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (thisCreature != null && opponentCreature != null) {
            return thisCreature.fight(opponentCreature, source, game);
        }
        return false;
    }

    @Override
    public GamorreanPrisonGuardEffect copy() {
        return new GamorreanPrisonGuardEffect(this);
    }
}
