package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class IsolatedTower extends CardImpl {

    public IsolatedTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Until end of turn, your opponents and creatures with hexproof they control can be the targets of spells and abilities you control as though they didn't have hexproof.
        Ability ability = new SimpleActivatedAbility(
                new IsolatedTowerEffect(),
                new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public IsolatedTower(final IsolatedTower card) {
        super(card);
    }

    @Override
    public IsolatedTower copy() {
        return new IsolatedTower(this);
    }
}

class IsolatedTowerEffect extends AsThoughEffectImpl {

    public IsolatedTowerEffect() {
        super(AsThoughEffectType.HEXPROOF, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, your opponents and "
                + "creatures with hexproof they control "
                + "can be the targets of spells and abilities "
                + "you control as though they didn't have hexproof";
    }

    public IsolatedTowerEffect(final IsolatedTowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IsolatedTowerEffect copy() {
        return new IsolatedTowerEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            if (game.getOpponents(source.getControllerId()).contains(sourceId)) {
                return true;
            }
            Permanent creature = game.getPermanent(sourceId);
            if (creature != null
                    && game.getOpponents(source.getControllerId()).contains(creature.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}
