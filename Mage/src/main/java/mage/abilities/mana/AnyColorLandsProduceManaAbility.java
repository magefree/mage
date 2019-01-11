
package mage.abilities.mana;

import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.ColoredManaSymbol;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class AnyColorLandsProduceManaAbility extends ActivatedManaAbilityImpl {

    public AnyColorLandsProduceManaAbility(TargetController targetController) {
        this(targetController, true);
    }

    public AnyColorLandsProduceManaAbility(TargetController targetController, boolean onlyColors) {
        this(targetController, onlyColors, null);
    }

    public AnyColorLandsProduceManaAbility(TargetController targetController, boolean onlyColors, FilterPermanent filter) {
        super(Zone.BATTLEFIELD, new AnyColorLandsProduceManaEffect(targetController, onlyColors, filter), new TapSourceCost());
    }

    public AnyColorLandsProduceManaAbility(final AnyColorLandsProduceManaAbility ability) {
        super(ability);
    }

    @Override
    public AnyColorLandsProduceManaAbility copy() {
        return new AnyColorLandsProduceManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        return ((AnyColorLandsProduceManaEffect) getEffects().get(0)).getNetMana(game, this);
    }

    @Override
    public boolean definesMana(Game game) {
        return true;
    }

}

class AnyColorLandsProduceManaEffect extends ManaEffect {

    private final FilterPermanent filter;
    private final boolean onlyColors; // false if mana types can be produced (also Colorless mana), if true only colors can be produced (no Colorless mana).

    private boolean inManaTypeCalculation = false;

    AnyColorLandsProduceManaEffect(TargetController targetController, boolean onlyColors, FilterPermanent filter) {
        super();
        if (filter == null) {
            this.filter = new FilterLandPermanent();
        } else {
            this.filter = filter.copy();
        }
        this.onlyColors = onlyColors;
        this.filter.add(new ControllerPredicate(targetController));
        String text = targetController == TargetController.OPPONENT ? "an opponent controls" : "you control";
        staticText = "Add one mana of any " + (this.onlyColors ? "color" : "type") + " that a "
                + (filter == null ? "land " : filter.getMessage() + " ") + text + " could produce";
    }

    private AnyColorLandsProduceManaEffect(final AnyColorLandsProduceManaEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.onlyColors = effect.onlyColors;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana mana = new Mana();
        Mana types = getManaTypes(game, source);
        Choice choice = new ChoiceColor(true);
        choice.getChoices().clear();
        choice.setMessage("Pick a mana color");
        if (types.getBlack() > 0) {
            choice.getChoices().add("Black");
        }
        if (types.getRed() > 0) {
            choice.getChoices().add("Red");
        }
        if (types.getBlue() > 0) {
            choice.getChoices().add("Blue");
        }
        if (types.getGreen() > 0) {
            choice.getChoices().add("Green");
        }
        if (types.getWhite() > 0) {
            choice.getChoices().add("White");
        }
        if (!onlyColors && types.getColorless() > 0) {
            choice.getChoices().add("Colorless");
        }
        if (types.getAny() > 0) {
            choice.getChoices().add("Black");
            choice.getChoices().add("Red");
            choice.getChoices().add("Blue");
            choice.getChoices().add("Green");
            choice.getChoices().add("White");
            if (!onlyColors) {
                choice.getChoices().add("Colorless");
            }

        }
        if (!choice.getChoices().isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                if (player == null || !player.choose(outcome, choice, game)) {
                    return null;
                }
            }
            if (choice.getChoice() != null) {
                switch (choice.getChoice()) {
                    case "Black":
                        mana.setBlack(1);
                        break;
                    case "Blue":
                        mana.setBlue(1);
                        break;
                    case "Red":
                        mana.setRed(1);
                        break;
                    case "Green":
                        mana.setGreen(1);
                        break;
                    case "White":
                        mana.setWhite(1);
                        break;
                    case "Colorless":
                        mana.setColorless(1);
                        break;
                }
            }
        }
        return mana;
    }

    private Mana getManaTypes(Game game, Ability source) {
        Mana types = new Mana();
        if (game == null || game.getPhase() == null) {
            return types;
        }
        if (inManaTypeCalculation) {
            return types;
        }
        inManaTypeCalculation = true;
        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent land : lands) {
            Abilities<ActivatedManaAbilityImpl> mana = land.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD);
            for (ActivatedManaAbilityImpl ability : mana) {
                if (!ability.equals(source) && ability.definesMana(game)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        types.add(netMana);
                    }
                }
            }
        }
        inManaTypeCalculation = false;
        return types;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netManas = new ArrayList<>();
        Mana types = getManaTypes(game, source);
        if (types.getBlack() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.B));
        }
        if (types.getRed() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.R));
        }
        if (types.getBlue() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.U));
        }
        if (types.getGreen() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.G));
        }
        if (types.getWhite() > 0) {
            netManas.add(new Mana(ColoredManaSymbol.W));
        }
        if (types.getColorless() > 0) {
            netManas.add(Mana.ColorlessMana(1));
        }
        if (types.getAny() > 0) {
            netManas.add(Mana.AnyMana(1));
        }
        return netManas;
    }

    @Override
    public AnyColorLandsProduceManaEffect copy() {
        return new AnyColorLandsProduceManaEffect(this);
    }
}
