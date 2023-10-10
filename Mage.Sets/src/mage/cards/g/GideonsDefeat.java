
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GideonsDefeat extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterAttackingOrBlockingCreature("white creature that's attacking or blocking");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public GideonsDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target white creature that's attacking or blocking. If it was a Gideon planeswalker, you gain 5 life.
        this.getSpellAbility().addEffect(new GideonsDefeatEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private GideonsDefeat(final GideonsDefeat card) {
        super(card);
    }

    @Override
    public GideonsDefeat copy() {
        return new GideonsDefeat(this);
    }
}

class GideonsDefeatEffect extends OneShotEffect {

    public GideonsDefeatEffect() {
        super(Outcome.Exile);
        staticText = "Exile target white creature that's attacking or blocking. If it was a Gideon planeswalker, you gain 5 life";
    }

    private GideonsDefeatEffect(final GideonsDefeatEffect effect) {
        super(effect);
    }

    @Override
    public GideonsDefeatEffect copy() {
        return new GideonsDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            controller.moveCards(permanent, Zone.EXILED, source, game);
            game.getState().processAction(game);
            if (permanent.isPlaneswalker(game) && permanent.hasSubtype(SubType.GIDEON, game)) {
                controller.gainLife(5, game, source);
            }
            return true;
        }
        return false;
    }

}
