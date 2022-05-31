package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodMoney extends CardImpl {

    public BloodMoney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Destroy all creatures. For each nontoken creature destroyed this way, create a tapped Treasure token.
        this.getSpellAbility().addEffect(new BloodMoneyEffect());
    }

    private BloodMoney(final BloodMoney card) {
        super(card);
    }

    @Override
    public BloodMoney copy() {
        return new BloodMoney(this);
    }
}

class BloodMoneyEffect extends OneShotEffect {

    BloodMoneyEffect() {
        super(Outcome.Benefit);
        staticText = "destroy all creatures. For each nontoken creature destroyed this way, create a tapped Treasure token";
    }

    private BloodMoneyEffect(final BloodMoneyEffect effect) {
        super(effect);
    }

    @Override
    public BloodMoneyEffect copy() {
        return new BloodMoneyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (permanent.destroy(source, game) && !(permanent instanceof PermanentToken)) {
                count++;
            }
        }
        if (count > 0) {
            new TreasureToken().putOntoBattlefield(count, game, source, source.getControllerId(), true, false);
        }
        return true;
    }
}
