
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class SquanderedResources extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public SquanderedResources(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{G}");

        // Sacrifice a land: Add one mana of any type the sacrificed land could produce.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new SquanderedResourcesEffect(), new SacrificeTargetCost(new TargetControlledPermanent(filter))));
    }

    public SquanderedResources(final SquanderedResources card) {
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
        if (types.getColorless() > 0) {
            choice.getChoices().add("Colorless");
        }
        if (types.getAny() > 0) {
            choice.getChoices().add("Black");
            choice.getChoices().add("Red");
            choice.getChoices().add("Blue");
            choice.getChoices().add("Green");
            choice.getChoices().add("White");
            choice.getChoices().add("Colorless");
        }
        Mana mana = new Mana();
        if (!choice.getChoices().isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                if (!player.choose(outcome, choice, game)) {
                    return null;
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
        if (types.getGeneric() > 0) {
            netManas.add(Mana.ColorlessMana(1));
        }
        return netManas;
    }

    private Mana getManaTypes(Game game, Ability source) {

        Mana types = new Mana();
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                Permanent land = ((SacrificeTargetCost) cost).getPermanents().get(0);
                if (land != null) {
                    Abilities<ActivatedManaAbilityImpl> manaAbilities = land.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD);
                    for (ActivatedManaAbilityImpl ability : manaAbilities) {
                        if (!ability.equals(source) && ability.definesMana(game)) {
                            for (Mana netMana : ability.getNetMana(game)) {
                                types.add(netMana);
                            }
                        }
                    }
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
