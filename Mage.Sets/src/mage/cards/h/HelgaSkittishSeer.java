package mage.cards.h;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HelgaSkittishSeer extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a creature spell with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 4));
    }

    public HelgaSkittishSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a creature spell with mana value 4 or greater, you draw a card, gain 1 life and put a +1/+1 counter on Helga, Skittish Seer
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1, true), filter, false
        );
        ability.addEffect(new GainLifeEffect(1).setText(", gain 1 life"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy(", and"));

        this.addAbility(ability);

        // {T}: Add X mana of any one color, where X is Helga, Skittish Seer's power. Use this mana only to cast creature spells with mana value 4 or greater or to cas creature spells with {x} in their mana costs
        this.addAbility(
                new SimpleManaAbility(
                        Zone.BATTLEFIELD,
                        new HelgaSkittishSeerManaEffect(new SourcePermanentPowerCount()),
                        new TapSourceCost()
                )
        );
    }

    private HelgaSkittishSeer(final HelgaSkittishSeer card) {
        super(card);
    }

    @Override
    public HelgaSkittishSeer copy() {
        return new HelgaSkittishSeer(this);
    }
}

class HelgaSkittishSeerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options){
        return new HelgaSkittishSeerConditionalMana(this.mana);
    }

    @Override
    public String getRule(){
        return "Spend this mana only to cast creature spells with mana value 4 or greater or creature spells with {X} in their mana costs";
    }
}

class HelgaSkittishSeerConditionalMana extends ConditionalMana {

    HelgaSkittishSeerConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast creature spells with mana value 4 or greater or creature spells with {X} in their mana costs";
        addCondition(new HelgaSkittishSeerManaCondition());
    }
}

class HelgaSkittishSeerManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        // ... Check if the mana is being used for a creature spell
        if (super.apply(game, source)){
            MageObject object = game.getObject(source);
            // ... Check if mana is used for spell wit mana value >= 4 or for spell with {x} in its mana cost
            if (object != null && (object.getManaValue() >= 4 || object.getManaCost().containsX())) {
                return true;
            }
        }
        return false;
    }
}

class HelgaSkittishSeerManaEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder = new HelgaSkittishSeerManaBuilder();
    private final DynamicValue power;

    HelgaSkittishSeerManaEffect(DynamicValue power) {
        this.power = power;
        this.staticText = "Add X mana of any one color, where X is {this}'s power. " + manaBuilder.getRule();
    }

    private HelgaSkittishSeerManaEffect(final HelgaSkittishSeerManaEffect effect) {
        super(effect);
        this.power = effect.power.copy();
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null){
            int currentPower = power.calculate(game, source, this);
            netMana.add(manaBuilder.setMana(Mana.BlackMana(currentPower), source, game).build());
            netMana.add(manaBuilder.setMana(Mana.BlueMana(currentPower), source, game).build());
            netMana.add(manaBuilder.setMana(Mana.RedMana(currentPower), source, game).build());
            netMana.add(manaBuilder.setMana(Mana.GreenMana(currentPower), source, game).build());
            netMana.add(manaBuilder.setMana(Mana.WhiteMana(currentPower), source, game).build());
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null){
            return mana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null){
            ChoiceColor choice = new ChoiceColor();
            if (!controller.choose(Outcome.PutManaInPool, choice, game)){
                return mana;
            }
            Mana chosen = choice.getMana(power.calculate(game, source, this));
            return manaBuilder.setMana(chosen, source, game).build();
        }
        return mana;
    }

    @Override
    public HelgaSkittishSeerManaEffect copy() {
        return new HelgaSkittishSeerManaEffect(this);
    }
}
