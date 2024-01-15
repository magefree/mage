package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UntapTargetCost;
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

import java.util.*;

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
        ability.addCost(new UntapTargetCost(
                new TargetLandPermanent(filter)
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

    BenthicExplorersManaEffect() {
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
        if (game == null) {
            return mana;
        }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return mana;
        }

        Choice choice = ManaType.getChoiceOfManaTypes(getManaTypes(game, source), false);
        if (choice.getChoices().isEmpty()) {
            return mana;
        }


        if (choice.getChoices().size() == 1) {
            choice.setChoice(choice.getChoices().iterator().next());
        } else {
            if (!player.choose(Outcome.PutManaInPool, choice, game)) {
                return mana;
            }
        }

        if (choice.getChoice() == null) {
            return mana;
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

        return mana;
    }

    private Set<ManaType> getManaTypes(Game game, Ability source) {
        Set<ManaType> types = EnumSet.noneOf(ManaType.class);
        if (game == null || game.getPhase() == null) {
            return types;
        }

        List<UUID> untapped = (List<UUID>) game.getState()
                .getValue("UntapTargetCost" + source.getSourceId().toString());
        Permanent land = game.getPermanentOrLKIBattlefield(untapped.get(0));
        if (land == null) {
            return types;
        }

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
