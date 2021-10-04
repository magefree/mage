package mage.cards.k;

import mage.MageInt;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author weirddan455
 */
public final class KatildaDawnhartPrime extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WEREWOLF, "Werewolves");
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent(SubType.HUMAN, "Human creatures");

    public KatildaDawnhartPrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from Werewolves
        this.addAbility(new ProtectionAbility(filter));

        // Human creatures you control have "{T}: Add one mana of any of this creature's colors."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new KatildaDawnhartPrimeManaAbility(), Duration.WhileOnBattlefield, filter2
        )));

        // {4}{G}{W}, {T}: Put a +1/+1 counter on each creature you control.
        Ability ability = new SimpleActivatedAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ), new ManaCostsImpl<>("{4}{G}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private KatildaDawnhartPrime(final KatildaDawnhartPrime card) {
        super(card);
    }

    @Override
    public KatildaDawnhartPrime copy() {
        return new KatildaDawnhartPrime(this);
    }
}

// Mana code based on CommanderColorIdentityManaAbility
class KatildaDawnhartPrimeManaAbility extends ActivatedManaAbilityImpl {

    public KatildaDawnhartPrimeManaAbility() {
        super(Zone.BATTLEFIELD, new KatildaDawnhartPrimeManaEffect(), new TapSourceCost());
    }

    private KatildaDawnhartPrimeManaAbility(final KatildaDawnhartPrimeManaAbility ability) {
        super(ability);
    }

    @Override
    public KatildaDawnhartPrimeManaAbility copy() {
        return new KatildaDawnhartPrimeManaAbility(this);
    }

    @Override
    public boolean definesMana(Game game) {
        return true;
    }
}

class KatildaDawnhartPrimeManaEffect extends ManaEffect {

    public KatildaDawnhartPrimeManaEffect() {
        super();
        staticText = "Add one mana of any of this creature's colors";
    }

    private KatildaDawnhartPrimeManaEffect(final KatildaDawnhartPrimeManaEffect effect) {
        super(effect);
    }

    @Override
    public KatildaDawnhartPrimeManaEffect copy() {
        return new KatildaDawnhartPrimeManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            if (permanent != null) {
                ObjectColor color = permanent.getColor(game);
                if (color.isWhite()) {
                    netMana.add(new Mana(ColoredManaSymbol.W));
                }
                if (color.isBlue()) {
                    netMana.add(new Mana(ColoredManaSymbol.U));
                }
                if (color.isBlack()) {
                    netMana.add(new Mana(ColoredManaSymbol.B));
                }
                if (color.isRed()) {
                    netMana.add(new Mana(ColoredManaSymbol.R));
                }
                if (color.isGreen()) {
                    netMana.add(new Mana(ColoredManaSymbol.G));
                }
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game != null) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            if (controller != null && permanent != null) {
                Choice choice = new ChoiceImpl();
                choice.setMessage("Pick a mana color");
                ObjectColor color = permanent.getColor(game);
                if (color.isWhite()) {
                    choice.getChoices().add("White");
                }
                if (color.isBlue()) {
                    choice.getChoices().add("Blue");
                }
                if (color.isBlack()) {
                    choice.getChoices().add("Black");
                }
                if (color.isRed()) {
                    choice.getChoices().add("Red");
                }
                if (color.isGreen()) {
                    choice.getChoices().add("Green");
                }
                if (!choice.getChoices().isEmpty()) {
                    if (choice.getChoices().size() == 1) {
                        choice.setChoice(choice.getChoices().iterator().next());
                    } else {
                        controller.choose(outcome, choice, game);
                    }

                    if (choice.getChoice() != null) {
                        switch (choice.getChoice()) {
                            case "White":
                                mana.setWhite(1);
                                break;
                            case "Blue":
                                mana.setBlue(1);
                                break;
                            case "Black":
                                mana.setBlack(1);
                                break;
                            case "Red":
                                mana.setRed(1);
                                break;
                            case "Green":
                                mana.setGreen(1);
                                break;
                        }
                    }
                }
            }
        }
        return mana;
    }
}
