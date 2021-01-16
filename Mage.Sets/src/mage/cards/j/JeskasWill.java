package mage.cards.j;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeskasWill extends CardImpl {

    public JeskasWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Choose one. If you control a commander as you cast this spell, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(ControlACommanderCondition.instance);

        // • Add {R} for each card in target opponent's hand.
        this.getSpellAbility().addEffect(new JeskasWillEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Exile the top three cards of your library. You may play them this turn.
        this.getSpellAbility().addMode(new Mode(new ExileTopXMayPlayUntilEndOfTurnEffect(3)));
    }

    private JeskasWill(final JeskasWill card) {
        super(card);
    }

    @Override
    public JeskasWill copy() {
        return new JeskasWill(this);
    }
}

class JeskasWillEffect extends OneShotEffect {

    JeskasWillEffect() {
        super(Outcome.Benefit);
        staticText = "Add {R} for each card in target opponent's hand.";
    }

    private JeskasWillEffect(final JeskasWillEffect effect) {
        super(effect);
    }

    @Override
    public JeskasWillEffect copy() {
        return new JeskasWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null || player.getHand().size() < 1) {
            return false;
        }
        controller.getManaPool().addMana(Mana.RedMana(player.getHand().size()), game, source);
        return true;
    }
}
