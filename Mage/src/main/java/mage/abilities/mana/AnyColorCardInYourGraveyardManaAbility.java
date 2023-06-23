package mage.abilities.mana;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.cards.Card;
import mage.choices.Choice;
import mage.constants.ManaType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Susucr
 */
public class AnyColorCardInYourGraveyardManaAbility extends ActivatedManaAbilityImpl {

    public AnyColorCardInYourGraveyardManaAbility(FilterCard cardFilter) {
        super(Zone.BATTLEFIELD, new AnyColorCardInYourGraveyardManaEffect(cardFilter), new TapSourceCost());
    }

    public AnyColorCardInYourGraveyardManaAbility(final AnyColorCardInYourGraveyardManaAbility ability) {
        super(ability);
    }

    @Override
    public AnyColorCardInYourGraveyardManaAbility copy() {
        return new AnyColorCardInYourGraveyardManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        return ((AnyColorCardInYourGraveyardManaEffect) getEffects().get(0)).getNetMana(game, this);
    }

    @Override
    public boolean definesMana(Game game) {
        return true;
    }

}

class AnyColorCardInYourGraveyardManaEffect extends ManaEffect {

    private final FilterCard filter;

    private transient boolean inManaTypeCalculation = false;

    public AnyColorCardInYourGraveyardManaEffect(FilterCard cardFilter) {
        super();
        filter = cardFilter;
        staticText = "Add one mana of any color" +
            " among " + cardFilter.getMessage() + " in your graveyard.";
    }

    public AnyColorCardInYourGraveyardManaEffect(final AnyColorCardInYourGraveyardManaEffect effect) {
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

        Set<Card> cards =
            game.getPlayer(source.getControllerId())
                .getGraveyard()
                .getCards(filter, game);
        for (Card card : cards) {
            ObjectColor cardColor = card.getColor(game);

            if (cardColor.isBlack()) {
                manaTypes.add(ManaType.BLACK);
            }
            if (cardColor.isBlue()) {
                manaTypes.add(ManaType.BLUE);
            }
            if (cardColor.isGreen()) {
                manaTypes.add(ManaType.GREEN);
            }
            if (cardColor.isRed()) {
                manaTypes.add(ManaType.RED);
            }
            if (cardColor.isWhite()) {
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
    public AnyColorCardInYourGraveyardManaEffect copy() {
        return new AnyColorCardInYourGraveyardManaEffect(this);
    }
}
