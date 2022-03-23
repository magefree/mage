package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandrasTriumph extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ChandrasTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Chandra's Triumph deals 3 damage to target creature or planeswalker an opponent controls. Chandra's Triumph deals 5 damage to that permanent instead
        // if you control a Chandra planeswalker.
        this.getSpellAbility().addEffect(new ChandrasTriumphEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ChandrasTriumph(final ChandrasTriumph card) {
        super(card);
    }

    @Override
    public ChandrasTriumph copy() {
        return new ChandrasTriumph(this);
    }
}

class ChandrasTriumphEffect extends OneShotEffect {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(SubType.CHANDRA);

    ChandrasTriumphEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to target creature or planeswalker an opponent controls. " +
                "{this} deals 5 damage to that permanent instead if you control a Chandra planeswalker.";
    }

    private ChandrasTriumphEffect(final ChandrasTriumphEffect effect) {
        super(effect);
    }

    @Override
    public ChandrasTriumphEffect copy() {
        return new ChandrasTriumphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int damage = 3;
        if (!game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).isEmpty()) {
            damage = 5;
        }
        return permanent.damage(damage, source.getSourceId(), source, game) > 0;
    }
}