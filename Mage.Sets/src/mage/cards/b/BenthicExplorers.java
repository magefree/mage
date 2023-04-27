package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BenthicExplorers extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("tapped land an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(TappedPredicate.TAPPED);
    }

    public BenthicExplorers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}, Untap a tapped land an opponent controls: Add one mana of any type that land could produce.
        Ability ability = new BenthicExplorersManaAbility();
        ability.addCost(new BenthicExplorersCost(
                new TargetLandPermanent(1, 1, filter, true)
        ));
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

    private final TargetLandPermanent target;

    public BenthicExplorersCost(TargetLandPermanent target) {
        this.target = target;
        this.text = "Untap " + target.getDescription();
    }

    public BenthicExplorersCost(final BenthicExplorersCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (target.choose(Outcome.Untap, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : target.getTargets()) {
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
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(controllerId, source, game);
    }

    @Override
    public BenthicExplorersCost copy() {
        return new BenthicExplorersCost(this);
    }

}

class BenthicExplorersManaAbility extends ActivatedManaAbilityImpl {

    BenthicExplorersManaAbility() {
        super(Zone.BATTLEFIELD, new BenthicExplorersManaEffect(), new TapSourceCost());
    }

    private BenthicExplorersManaAbility(BenthicExplorersManaAbility ability) {
        super(ability);
    }

    @Override
    public BenthicExplorersManaAbility copy() {
        return new BenthicExplorersManaAbility(this);
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
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game == null) {
            return new ArrayList<>();
        }
        Set<ManaType> manaTypes = EnumSet.noneOf(ManaType.class);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponentId)) {
                if (permanent.isLand(game) && permanent.isTapped()) {
                    for (ActivatedManaAbilityImpl ability : permanent.getAbilities(game).getActivatedManaAbilities(Zone.BATTLEFIELD)) {
                        manaTypes.addAll(ability.getProducableManaTypes(game));
                    }
                }
            }
        }
        return ManaType.getManaListFromManaTypes(manaTypes, false);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) { return mana; }

        Choice choice = ManaType.getChoiceOfManaTypes(getManaTypes(game, source), false);
        if (choice.getChoices().isEmpty()) { return mana; }

        Player player = game.getPlayer(source.getControllerId());
        if (choice.getChoices().size() == 1) {
            choice.setChoice(choice.getChoices().iterator().next());
        } else {
            if (player == null
                    || !player.choose(Outcome.Neutral, choice, game)) {
                return mana;
            }
        }

        if (choice.getChoice() == null) { return mana; }

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

        return mana;
    }

    private Set<ManaType> getManaTypes(Game game, Ability source) {
        Set<ManaType> types = EnumSet.noneOf(ManaType.class);
        if (game == null || game.getPhase() == null) {
            return types;
        }

        Permanent land = (Permanent) game.getState().getValue("UntapTargetCost" + source.getSourceId().toString());
        if (land == null) { return types; }

        Abilities<ActivatedManaAbilityImpl> mana = land.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD);
        for (ActivatedManaAbilityImpl ability : mana) {
            if (ability.definesMana(game)) {
                types.addAll(ability.getProducableManaTypes(game));
            }
        }
        return types;
    }

    @Override
    public BenthicExplorersManaEffect copy() {
        return new BenthicExplorersManaEffect(this);
    }
}
