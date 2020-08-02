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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import mage.constants.ManaType;

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

    public static Set<ManaType> getManaTypesFromPermanent(Permanent permanent, Game game) {
        Set<ManaType> allTypes = new HashSet<>();
        if (permanent != null) {
            Abilities<ActivatedManaAbilityImpl> manaAbilities = permanent.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD);
            for (ActivatedManaAbilityImpl ability : manaAbilities) {
                allTypes.addAll(ability.getProducableManaTypes(game));
            }
        }
        return allTypes;
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
        this.filter.add(targetController.getControllerPredicate());
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
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netManas = new ArrayList<>();
        if (game != null) {
            Set<ManaType> manaTypes = getManaTypes(game, source);
            if ((manaTypes.size() == 5 && !manaTypes.contains(ManaType.COLORLESS)) || manaTypes.size() == 6) { // GENERIC should never be returned from getManaTypes
                netManas.add(Mana.AnyMana(1));
            } else {
                if (manaTypes.contains(ManaType.BLACK)) {
                    netManas.add(Mana.BlackMana(1));
                }
                if (manaTypes.contains(ManaType.RED)) {
                    netManas.add(Mana.RedMana(1));
                }
                if (manaTypes.contains(ManaType.BLUE)) {
                    netManas.add(Mana.BlueMana(1));
                }
                if (manaTypes.contains(ManaType.GREEN)) {
                    netManas.add(Mana.GreenMana(1));
                }
                if (manaTypes.contains(ManaType.WHITE)) {
                    netManas.add(Mana.WhiteMana(1));
                }
            }
            if (!onlyColors && manaTypes.contains(ManaType.COLORLESS)) {
                netManas.add(Mana.ColorlessMana(1));
            }
        }
        return netManas;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Set<ManaType> types = getManaTypes(game, source);
        Choice choice = new ChoiceColor(true);
        choice.getChoices().clear();
        choice.setMessage("Pick a mana " + (onlyColors ? "color" : "type"));
        if (types.contains(ManaType.BLACK)) {
            choice.getChoices().add("Black");
        }
        if (types.contains(ManaType.RED)) {
            choice.getChoices().add("Red");
        }
        if (types.contains(ManaType.BLUE)) {
            choice.getChoices().add("Blue");
        }
        if (types.contains(ManaType.GREEN)) {
            choice.getChoices().add("Green");
        }
        if (types.contains(ManaType.WHITE)) {
            choice.getChoices().add("White");
        }
        if (types.contains(ManaType.COLORLESS)) {
            choice.getChoices().add("Colorless");
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

    private Set<ManaType> getManaTypes(Game game, Ability source) {
        Set types = new HashSet<>();
        if (game == null || game.getPhase() == null) {
            return types;
        }
        if (inManaTypeCalculation) { // Stop endless loops
            return types;
        }
        inManaTypeCalculation = true;
        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent land : lands) {
            if (!land.getId().equals(source.getSourceId())) {
                types.addAll(AnyColorLandsProduceManaAbility.getManaTypesFromPermanent(land, game));
            }
        }
        inManaTypeCalculation = false;
        return types;
    }

    @Override
    public AnyColorLandsProduceManaEffect copy() {
        return new AnyColorLandsProduceManaEffect(this);
    }

}
