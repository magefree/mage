package mage.cards.s;

import java.util.*;
import java.util.stream.Collectors;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.abilities.mana.ManaOptions;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class SquanderedResources extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public SquanderedResources(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{G}");

        // Sacrifice a land: Add one mana of any type the sacrificed land could produce.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new SquanderedResourcesEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    private SquanderedResources(final SquanderedResources card) {
        super(card);
    }

    @Override
    public SquanderedResources copy() {
        return new SquanderedResources(this);
    }
}

class SquanderedResourcesEffect extends ManaEffect {

    public SquanderedResourcesEffect() {
        super();
        staticText = "Add one mana of any type the sacrificed land could produce";
    }

    public SquanderedResourcesEffect(final SquanderedResourcesEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game != null && game.inCheckPlayableState()) {
            // add color combinations of available mana
            ManaOptions allPossibleMana = new ManaOptions();
            for (Permanent land : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), game)) {
                ManaOptions currentPossibleMana = new ManaOptions();
                Set<ManaType> manaTypes = AnyColorLandsProduceManaAbility.getManaTypesFromPermanent(land, game);
                if (manaTypes.size() == 5 && !manaTypes.contains(ManaType.COLORLESS) || manaTypes.size() == 6) {
                    currentPossibleMana.add(Mana.AnyMana(1));
                    if (manaTypes.contains(ManaType.COLORLESS)) {
                        currentPossibleMana.add(new Mana(ManaType.COLORLESS));
                    }
                } else {
                    for (ManaType manaType : manaTypes) {
                        currentPossibleMana.add(new Mana(manaType));
                    }
                }
                allPossibleMana.addMana(currentPossibleMana);
            }
            allPossibleMana.removeFullyIncludedVariations();
            return new ArrayList<>(allPossibleMana);
        }
        return ManaType.getManaListFromManaTypes(getManaTypesFromSacrificedPermanent(game, source), false);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Choice choice = ManaType.getChoiceOfManaTypes(getManaTypesFromSacrificedPermanent(game, source), false);        
        if (!choice.getChoices().isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return mana;
            }
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                if (!player.choose(outcome, choice, game)) {
                    return mana;
                }
            }
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
        return mana;
    }

    private Set<ManaType> getManaTypesFromSacrificedPermanent(Game game, Ability source) {
        Set<ManaType> types = new HashSet<>();
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                Permanent land = ((SacrificeTargetCost) cost).getPermanents().get(0);
                if (land != null) {
                    types.addAll(AnyColorLandsProduceManaAbility.getManaTypesFromPermanent(land, game));
                    break;
                }
            }
        }
        return types;
    }

    @Override
    public SquanderedResourcesEffect copy() {
        return new SquanderedResourcesEffect(this);
    }
}
