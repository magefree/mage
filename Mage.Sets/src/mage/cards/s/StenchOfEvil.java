package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class StenchOfEvil extends CardImpl {

    public StenchOfEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Destroy all Plains. For each land destroyed this way, Stench of Evil deals 1 damage to that land's controller unless they pay {2}.
        this.getSpellAbility().addEffect(new StenchOfEvilEffect());

    }

    private StenchOfEvil(final StenchOfEvil card) {
        super(card);
    }

    @Override
    public StenchOfEvil copy() {
        return new StenchOfEvil(this);
    }
}

class StenchOfEvilEffect extends OneShotEffect {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    public StenchOfEvilEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy all Plains. For each land destroyed this way, {this} deals 1 damage to that land's controller unless they pay {2}";
    }

    public StenchOfEvilEffect(final StenchOfEvilEffect effect) {
        super(effect);
    }

    @Override
    public StenchOfEvilEffect copy() {
        return new StenchOfEvilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent land : game.getBattlefield().getAllActivePermanents(filter, game)) {
            UUID landControllerId = land.getControllerId();
            if (land.destroy(source, game, false)) {
                Cost cost = new ManaCostsImpl<>("{2}");
                Player landController = game.getPlayer(landControllerId);
                if (landController != null
                        && cost.canPay(source, source, landControllerId, game)
                        && !cost.pay(source, game, source, landControllerId, false)) {
                    landController.damage(1, source.getSourceId(), source, game);
                }
            }
        }
        return true;
    }
}
