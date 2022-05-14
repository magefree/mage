package mage.cards.i;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IncubationDruid extends CardImpl {

    public IncubationDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Add one mana of any type that a land you control could produce. If Incubation Druid has a +1/+1 counter on it, add three mana of that type instead.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AnyColorLandsProduceManaEffect(), new TapSourceCost()
        ));

        // {3}{G}{G}: Adapt 3.
        this.addAbility(new AdaptAbility(3, "{3}{G}{G}"));
    }

    private IncubationDruid(final IncubationDruid card) {
        super(card);
    }

    @Override
    public IncubationDruid copy() {
        return new IncubationDruid(this);
    }
}

class AnyColorLandsProduceManaEffect extends ManaEffect {

    private boolean inManaTypeCalculation;

    AnyColorLandsProduceManaEffect() {
        super();
        staticText = "Add one mana of any type that a land you control could produce. " +
                "If {this} has a +1/+1 counter on it, add three mana of that type instead.";
    }

    private AnyColorLandsProduceManaEffect(final AnyColorLandsProduceManaEffect effect) {
        super(effect);
        this.inManaTypeCalculation = effect.inManaTypeCalculation;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        int manaAmount = getManaAmount(game, source);
        List<Mana> netManas = new ArrayList<>();
        Mana types = getManaTypes(game, source);
        if (types.getRed() > 0) {
            netManas.add(Mana.RedMana(manaAmount));
        }
        if (types.getGreen() > 0) {
            netManas.add(Mana.GreenMana(manaAmount));
        }
        if (types.getBlue() > 0) {
            netManas.add(Mana.BlueMana(manaAmount));
        }
        if (types.getWhite() > 0) {
            netManas.add(Mana.WhiteMana(manaAmount));
        }
        if (types.getBlack() > 0) {
            netManas.add(Mana.BlackMana(manaAmount));
        }
        if (types.getColorless() > 0) {
            netManas.add(Mana.ColorlessMana(manaAmount));
        }
        if (types.getAny() > 0) {
            netManas.add(Mana.AnyMana(manaAmount));
        }
        return netManas;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        int manaAmount = getManaAmount(game, source);
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
            } else if (player == null || !player.choose(outcome, choice, game)) {
                return mana;
            }
            if (choice.getChoice() != null) {
                switch (choice.getChoice()) {
                    case "Black":
                        mana.setBlack(manaAmount);
                        break;
                    case "Blue":
                        mana.setBlue(manaAmount);
                        break;
                    case "Red":
                        mana.setRed(manaAmount);
                        break;
                    case "Green":
                        mana.setGreen(manaAmount);
                        break;
                    case "White":
                        mana.setWhite(manaAmount);
                        break;
                    case "Colorless":
                        mana.setColorless(manaAmount);
                        break;
                }
            }
        }
        return mana;
    }

    @Override
    public AnyColorLandsProduceManaEffect copy() {
        return new AnyColorLandsProduceManaEffect(this);
    }

    private int getManaAmount(Game game, Ability source) {
        if (game != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && permanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
                return 3;
            }
        }
        return 1;
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
        List<Permanent> lands = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), source, game);
        for (Permanent land : lands) {
            Abilities<ActivatedManaAbilityImpl> mana = land.getAbilities().getActivatedManaAbilities(Zone.BATTLEFIELD);
            for (ActivatedManaAbilityImpl ability : mana) {
                if (!ability.equals(source) && ability.definesMana(game)) {
                    for (Mana netMana : ability.getNetMana(game)) {
                        types.add(netMana);
                    }
                }
            }
        }
        inManaTypeCalculation = false;
        return types;
    }
}
