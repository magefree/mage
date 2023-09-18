package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Rafbill
 */
public final class FrightfulDelusion extends CardImpl {

    public FrightfulDelusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");


        // Counter target spell unless its controller pays {1}. That player discards a card.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new FrightfulDelusionEffect());
    }

    private FrightfulDelusion(final FrightfulDelusion card) {
        super(card);
    }

    @Override
    public FrightfulDelusion copy() {
        return new FrightfulDelusion(this);
    }
}

class FrightfulDelusionEffect extends OneShotEffect {

    public FrightfulDelusionEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell unless its controller pays {1}. That player discards a card.";
    }

    private FrightfulDelusionEffect(final FrightfulDelusionEffect effect) {
        super(effect);
    }

    @Override
    public FrightfulDelusionEffect copy() {
        return new FrightfulDelusionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(
                targetPointer.getFirst(game, source));
        Cost cost = ManaUtil.createManaCost(1, false);
        if (spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                cost.clearPaid();
                game.getPlayer(spell.getControllerId()).discard(1, false, false, source, game);
                if (!cost.pay(source, game, source, spell.getControllerId(), false, null)) {
                    return game.getStack().counter(source.getFirstTarget(), source, game);
                }
            }
        }
        return false;
    }

}
