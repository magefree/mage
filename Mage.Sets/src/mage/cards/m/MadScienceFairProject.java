package mage.cards.m;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ManaChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class MadScienceFairProject extends CardImpl {

    public MadScienceFairProject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Roll a six-sided die. On a 3 or lower, target player adds {C}. Otherwise, that player adds one mana of any color they choose.
        Ability ability = new SimpleActivatedAbility(new MadScienceFairProjectEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private MadScienceFairProject(final MadScienceFairProject card) {
        super(card);
    }

    @Override
    public MadScienceFairProject copy() {
        return new MadScienceFairProject(this);
    }
}

class MadScienceFairProjectEffect extends OneShotEffect {

    MadScienceFairProjectEffect() {
        super(Outcome.Benefit);
        staticText = "Roll a six-sided die. On a 3 or lower, target player adds {C}. " +
                "Otherwise, that player adds one mana of any color they choose";
    }

    private MadScienceFairProjectEffect(final MadScienceFairProjectEffect effect) {
        super(effect);
    }

    @Override
    public MadScienceFairProjectEffect copy() {
        return new MadScienceFairProjectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        int amount = controller.rollDice(outcome, source, game, 6);
        Mana mana = amount <= 3 ? Mana.ColorlessMana(1) : ManaChoice.chooseAnyColor(player, game, 1);
        player.getManaPool().addMana(mana, game, source);
        return true;
    }
}
