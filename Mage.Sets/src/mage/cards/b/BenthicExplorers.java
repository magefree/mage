package mage.cards.b;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class BenthicExplorers extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("tapped land an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(TappedPredicate.instance);
    }

    public BenthicExplorers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {tap}, Untap a tapped land an opponent controls: Add one mana of any type that land could produce.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BenthicExplorersManaEffect(), new TapSourceCost());
        ability.addCost(new BenthicExplorersCost(new TargetLandPermanent(filter)));
        this.addAbility(ability);

    }

    private BenthicExplorers(final BenthicExplorers card) {
        super(card);
    }

    @Override
    public BenthicExplorers copy() {
        return new BenthicExplorers(this);
    }
}

class BenthicExplorersCost extends CostImpl {

    TargetLandPermanent target;

    public BenthicExplorersCost(TargetLandPermanent target) {
        this.target = target;
        this.text = "Untap " + CardUtil.numberToText(target.getMaxNumberOfTargets(), "") + ' ' + target.getTargetName();
    }

    public BenthicExplorersCost(final BenthicExplorersCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        if (target.choose(Outcome.Untap, controllerId, sourceId, game)) {
            for (UUID targetId : (List<UUID>) target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.untap(game);
                if (paid) {
                    game.getState().setValue("UntapTargetCost" + ability.getSourceId().toString(), permanent); // remember the untapped permanent
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return target.canChoose(controllerId, game);
    }

    @Override
    public BenthicExplorersCost copy() {
        return new BenthicExplorersCost(this);
    }

}

class BenthicExplorersManaEffect extends ManaEffect {

    public BenthicExplorersManaEffect() {
        staticText = "Add one mana of any type that land could produce";
    }

    private BenthicExplorersManaEffect(final BenthicExplorersManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            //List<Mana> mana = getNetMana(game, source);
            Mana manaToProduce = produceMana(true, game, source);
            controller.getManaPool().addMana(manaToProduce, game, source);
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
        if (!choice.getChoices().isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            if (choice.getChoices().size() == 1) {
                choice.setChoice(choice.getChoices().iterator().next());
            } else {
                if (player == null
                        || !player.choose(Outcome.Neutral, choice, game)) {
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
        if (game == null
                || game.getPhase() == null) {
            return types;
        }
        Permanent land = (Permanent) game.getState().getValue("UntapTargetCost" + source.getSourceId().toString());
        if (land != null) {
            System.out.println("The land is " + land.getName());
            Abilities<ActivatedManaAbilityImpl> mana = land.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD);
            for (ActivatedManaAbilityImpl ability : mana) {
                if (ability.definesMana(game)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        types.add(netMana);
                    }
                }
            }
        }
        System.out.println("The types : " + types.toString());
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
    public BenthicExplorersManaEffect copy() {
        return new BenthicExplorersManaEffect(this);
    }
}
