package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
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
 * @author LevelX2
 */
public final class OppressiveWill extends CardImpl {

    public OppressiveWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Counter target spell unless its controller pays {1} for each card in your hand.
        this.getSpellAbility().addEffect(new SpellSyphonEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private OppressiveWill(final OppressiveWill card) {
        super(card);
    }

    @Override
    public OppressiveWill copy() {
        return new OppressiveWill(this);
    }
}

class SpellSyphonEffect extends OneShotEffect {

    public SpellSyphonEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell unless its controller pays {1} for each card in your hand";
    }

    public SpellSyphonEffect(final SpellSyphonEffect effect) {
        super(effect);
    }

    @Override
    public SpellSyphonEffect copy() {
        return new SpellSyphonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && spell != null) {
            Player player = game.getPlayer(spell.getControllerId());
            Player controller = game.getPlayer(source.getControllerId());
            if (player != null && controller != null) {
                int amount = controller.getHand().size();
                if (amount > 0) {
                    Cost cost = ManaUtil.createManaCost(amount, false);
                    if (!cost.pay(source, game, source, spell.getControllerId(), false)) {
                        game.informPlayers(sourceObject.getLogName() + ": cost wasn't payed - countering target spell.");
                        return game.getStack().counter(source.getFirstTarget(), source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
