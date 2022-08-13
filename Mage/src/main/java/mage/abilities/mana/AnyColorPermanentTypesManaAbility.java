package mage.abilities.mana;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author CountAndromalius
 */
public class AnyColorPermanentTypesManaAbility extends ActivatedManaAbilityImpl {

    public AnyColorPermanentTypesManaAbility(TargetController targetController, FilterPermanent permanentTypes) {
        super(Zone.BATTLEFIELD, new AnyColorPermanentTypesManaEffect(targetController, permanentTypes), new TapSourceCost());
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

    private transient boolean inManaTypeCalculation = false;

    public AnyColorPermanentTypesManaEffect(TargetController targetController, FilterPermanent permanentTypes) {
        super();
        filter = permanentTypes;
        filter.add(targetController.getControllerPredicate());
        staticText = "Add one mana of any color" +
                " among " + permanentTypes.getMessage() + " " +
                (targetController == TargetController.OPPONENT ? "an opponent controls." : "you control.");
    }

    public AnyColorPermanentTypesManaEffect(final AnyColorPermanentTypesManaEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return game == null ? new ArrayList<>() : ManaType.getManaListFromManaTypes(getManaTypes(game, source), true);
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

        Choice choice = ManaType.getChoiceOfManaTypes(types, true);
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
        Set<ManaType> manaTypes = new HashSet<>(6);
        if (game == null || game.getPhase() == null || inManaTypeCalculation) {
            return manaTypes;
        }
        inManaTypeCalculation = true;

        ObjectColor permanentColor;
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        for (Permanent permanent : permanents) {
            permanentColor = permanent.getColor(game);

            if (permanentColor.isBlack()) {
                manaTypes.add(ManaType.BLACK);
            }
            if (permanentColor.isBlue()) {
                manaTypes.add(ManaType.BLUE);
            }
            if (permanentColor.isGreen()) {
                manaTypes.add(ManaType.GREEN);
            }
            if (permanentColor.isRed()) {
                manaTypes.add(ManaType.RED);
            }
            if (permanentColor.isWhite()) {
                manaTypes.add(ManaType.WHITE);
            }

            // If all types are already added, exit early
            if (manaTypes.size() == 5) {
                break;
            }
        }
        inManaTypeCalculation = false;
        return manaTypes;
    }

    @Override
    public AnyColorPermanentTypesManaEffect copy() {
        return new AnyColorPermanentTypesManaEffect(this);
    }
}
