package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BranchOfVituGhazi extends CardImpl {

    public BranchOfVituGhazi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Disguise {3}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{3}")));

        // When Branch of Vitu-Ghazi is turned face up, add two mana of any one color. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new BranchOfVituGhaziEffect()));
    }

    private BranchOfVituGhazi(final BranchOfVituGhazi card) {
        super(card);
    }

    @Override
    public BranchOfVituGhazi copy() {
        return new BranchOfVituGhazi(this);
    }
}

class BranchOfVituGhaziEffect extends OneShotEffect {

    BranchOfVituGhaziEffect() {
        super(Outcome.Benefit);
        staticText = "add two mana of any one color. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private BranchOfVituGhaziEffect(final BranchOfVituGhaziEffect effect) {
        super(effect);
    }

    @Override
    public BranchOfVituGhaziEffect copy() {
        return new BranchOfVituGhaziEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ChoiceColor choice = new ChoiceColor(true, "Choose a color of mana to add");
        player.choose(outcome, choice, game);
        player.getManaPool().addMana(choice.getMana(2), game, source, true);
        return true;
    }
}
