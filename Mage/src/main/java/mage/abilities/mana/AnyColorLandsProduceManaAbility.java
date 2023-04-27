package mage.abilities.mana;

import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.choices.Choice;
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
        Set<ManaType> allTypes = new HashSet<>(6);
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

    private transient boolean inManaTypeCalculation = false;

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
        return game == null ? new ArrayList<>() : ManaType.getManaListFromManaTypes(getManaTypes(game, source), onlyColors);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return null;
        }

        Set<ManaType> types = getManaTypes(game, source);
        if (types.isEmpty()) {
            return null;
        }

        Choice choice = ManaType.getChoiceOfManaTypes(types, onlyColors);
        if (choice.getChoices().size() == 1) {
            choice.setChoice(choice.getChoices().iterator().next());
        } else {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null || !player.choose(outcome, choice, game)) {
                return null;
            }
        }

        ManaType chosenType = ManaType.findByName(choice.getChoice());
        return chosenType == null ? null : new Mana(chosenType);
    }

    private Set<ManaType> getManaTypes(Game game, Ability source) {
        Set<ManaType> types = new HashSet<>(6);
        if (game == null || game.getPhase() == null) {
            return types;
        }
        if (inManaTypeCalculation) { // Stop endless loops
            return types;
        }
        inManaTypeCalculation = true;
        List<Permanent> lands = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        for (Permanent land : lands) {
            if (!land.getId().equals(source.getSourceId())) {
                types.addAll(AnyColorLandsProduceManaAbility.getManaTypesFromPermanent(land, game));
            }
        }
        inManaTypeCalculation = false;

        if (onlyColors) {
            types.remove(ManaType.COLORLESS);
        }

        return types;
    }

    @Override
    public AnyColorLandsProduceManaEffect copy() {
        return new AnyColorLandsProduceManaEffect(this);
    }

}
