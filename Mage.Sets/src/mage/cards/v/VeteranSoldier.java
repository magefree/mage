package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.AttacksOpponentWithMostLifeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeteranSoldier extends CardImpl {

    public VeteranSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever this creature attacks a player, if no opponent has more life than that that player, for each opponent, create a 1/1 white Soldier creature token that's tapped and attacking that player."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new AttacksOpponentWithMostLifeTriggeredAbility(new VeteranSoldierEffect(), false),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private VeteranSoldier(final VeteranSoldier card) {
        super(card);
    }

    @Override
    public VeteranSoldier copy() {
        return new VeteranSoldier(this);
    }
}

class VeteranSoldierEffect extends OneShotEffect {

    VeteranSoldierEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, create a 1/1 white Soldier creature token that's tapped and attacking that player";
    }

    private VeteranSoldierEffect(final VeteranSoldierEffect effect) {
        super(effect);
    }

    @Override
    public VeteranSoldierEffect copy() {
        return new VeteranSoldierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (game.getPlayer(opponentId) != null) {
                new SoldierToken().putOntoBattlefield(
                        1, game, source, source.getControllerId(),
                        true, true, opponentId
                );
            }
        }
        return true;
    }
}
