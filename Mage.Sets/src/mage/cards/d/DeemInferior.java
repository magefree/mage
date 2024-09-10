package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DeemInferior extends CardImpl {

    public DeemInferior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // This spell costs {1} less to cast for each card you've drawn this turn.
        Ability ability = new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(CardsDrawnThisTurnDynamicValue.instance)
                        .setText("this spell costs {1} less to cast for each card you've drawn this turn")
        );
        this.addAbility(ability.addHint(CardsDrawnThisTurnDynamicValue.getHint()));

        // The owner of target nonland permanent puts it into their library second from the top or on the bottom.
        this.getSpellAbility().addEffect(new DeemInferiorEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private DeemInferior(final DeemInferior card) {
        super(card);
    }

    @Override
    public DeemInferior copy() {
        return new DeemInferior(this);
    }
}

// Same as Temporal Cleansing.
class DeemInferiorEffect extends OneShotEffect {

    DeemInferiorEffect() {
        super(Outcome.Benefit);
        staticText = "the owner of target nonland permanent puts it into their library second from the top or on the bottom";
    }

    private DeemInferiorEffect(final DeemInferiorEffect effect) {
        super(effect);
    }

    @Override
    public DeemInferiorEffect copy() {
        return new DeemInferiorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getOwnerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(
                outcome, "Put " + permanent.getIdName() + " second from the top or on the bottom?",
                null, "Second from top", "Bottom", source, game
        )) {
            return player.putCardOnTopXOfLibrary(permanent, game, source, 2, true);
        }
        return player.putCardsOnBottomOfLibrary(permanent, game, source, false);
    }
}
