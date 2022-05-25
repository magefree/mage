package mage.abilities.mana;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.ColoredManaSymbol;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CountAndromalius
 */
public class AnyColorPermanentTypesManaAbility extends ActivatedManaAbilityImpl {

    public AnyColorPermanentTypesManaAbility(TargetController targetController, FilterPermanent permanentTypes) {
        this(targetController, true, permanentTypes);
    }

    public AnyColorPermanentTypesManaAbility(TargetController targetController, boolean onlyColors, FilterPermanent permanentTypes) {
        super(Zone.BATTLEFIELD, new AnyColorPermanentTypesManaEffect(targetController, onlyColors, permanentTypes), new TapSourceCost());
    }

    public AnyColorPermanentTypesManaAbility(final AnyColorPermanentTypesManaAbility ability) {
        super(ability);
    }

    @Override
    public AnyColorPermanentTypesManaAbility copy() {
        return new AnyColorPermanentTypesManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        return ((AnyColorPermanentTypesManaEffect) getEffects().get(0)).getNetMana(game, this);
    }

    @Override
    public boolean definesMana(Game game) {
        return true;
    }

}

class AnyColorPermanentTypesManaEffect extends ManaEffect {

    private final FilterPermanent filter;
    private final boolean onlyColors; // false if mana types can be produced (also Colorless mana), if true only colors can be produced (no Colorless mana).

    private boolean inManaTypeCalculation = false;

    public AnyColorPermanentTypesManaEffect(TargetController targetController, boolean onlyColors, FilterPermanent permanentTypes) {
        super();
        filter = permanentTypes;
        this.onlyColors = onlyColors;
        filter.add(targetController.getControllerPredicate());
        String text = targetController == TargetController.OPPONENT ? "an opponent controls." : "you control.";
        staticText = "Add one mana of any " + (this.onlyColors ? "color" : "type") + " among " + permanentTypes.getMessage() + " " + text;
    }

    public AnyColorPermanentTypesManaEffect(final AnyColorPermanentTypesManaEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.onlyColors = effect.onlyColors;
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
        if (!onlyColors && types.getColorless() > 0) {
            netManas.add(Mana.ColorlessMana(1));
        }
        if (types.getAny() > 0) {
            netManas.add(Mana.AnyMana(1));
        }
        return netManas;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
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
                if (!player.choose(outcome, choice, game)) {
                    return mana;
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

        ObjectColor permanentColor;

        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);

        for (Permanent permanent : permanents) {
            permanentColor = permanent.getColor(game);
            if (permanentColor.isColorless()) {
                types.add(Mana.ColorlessMana(1));
            } else {
                List<ObjectColor> permanentColors = permanent.getColor(game).getColors();
                for (ObjectColor color : permanentColors) {
                    types.add(new Mana(color.getOneColoredManaSymbol()));
                }
            }
        }
        inManaTypeCalculation = false;
        return types;
    }

    @Override
    public AnyColorPermanentTypesManaEffect copy() {
        return new AnyColorPermanentTypesManaEffect(this);
    }
}
