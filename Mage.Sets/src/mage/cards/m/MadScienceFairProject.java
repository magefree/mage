package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class MadScienceFairProject extends CardImpl {

    public MadScienceFairProject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Roll a six-sided die. On a 3 or lower, target player adds {C}. Otherwise, that player adds one mana of any color he or she chooses.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new MadScienceFairManaEffect(), new TapSourceCost()));
    }

    public MadScienceFairProject(final MadScienceFairProject card) {
        super(card);
    }

    @Override
    public MadScienceFairProject copy() {
        return new MadScienceFairProject(this);
    }
}

class MadScienceFairManaEffect extends ManaEffect {

    public MadScienceFairManaEffect() {
        super();
        this.staticText = "Roll a six-sided die. On a 3 or lower, target player adds {C}. Otherwise, that player adds one mana of any color he or she chooses";
    }

    public MadScienceFairManaEffect(final MadScienceFairManaEffect effect) {
        super(effect);
    }

    @Override
    public MadScienceFairManaEffect copy() {
        return new MadScienceFairManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        if (netMana) {
            return null;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = controller.rollDice(game, 6);
            if (amount <= 3) {
                return Mana.ColorlessMana(1);
            } else {
                ChoiceColor choice = new ChoiceColor();
                if (controller.choose(Outcome.PutManaInPool, choice, game)) {
                    Mana chosen = choice.getMana(1);
                    return chosen;
                }
            }
        }
        return null;
    }

}
