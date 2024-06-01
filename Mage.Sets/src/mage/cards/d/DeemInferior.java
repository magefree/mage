package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutIntoLibraryNFromTopTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author grimreap124
 */
public final class DeemInferior extends CardImpl {

    public DeemInferior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.SORCERY }, "{3}{U}");

        // This spell costs {1} less to cast for each card you've drawn this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, CardsDrawnThisTurnDynamicValue.instance))
                .addHint(new ValueHint("Cards you've drawn this turn", CardsDrawnThisTurnDynamicValue.instance)));
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

class DeemInferiorEffect extends OneShotEffect {

    DeemInferiorEffect() {
        super(Outcome.Detriment);
        this.staticText = "The owner of target nonland permanent puts it into their library second from the top or on the bottom";
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
        Permanent target = game.getPermanent(source.getTargets().getFirstTarget());
        if (target == null) {
            return false;
        }
        Player owner = game.getPlayer(target.getControllerId());

        if (owner == null) {
            return false;
        }

        if (owner.chooseUse(outcome,
                "Put " + target.getName() + " into your library second from the top or on the bottom?", null,
                "Second from the top", "Bottom", source, game)) {
            new PutIntoLibraryNFromTopTargetEffect(2).apply(game, source);
        } else {
            owner.putCardsOnBottomOfLibrary(target, game, source, false);
        }
        return true;
    }
}