package mage.cards.h;

import java.util.UUID;

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
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author anonymous
 */
public final class HelgaSkittishSeer extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public HelgaSkittishSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast a creature spell with mana value 4 or greater, you draw a card, gain 1 life, and put a +1/+1 counter on Helga, Skittish Seer.
        Ability ability = new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1).setText("draw a card"),filter,false);
        ability.addEffect(new GainLifeEffect(1).setText("gain 1 life"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("and put a +1/+1 counter on Helga, Skittish Seer"));

        this.addAbility(ability); 
        
        // {T}: Add X mana of any one color, where X is Helga, Skittish Seer's power. Spend this mana only to cast creature spells with mana value 4 or greater or creature spells with {X} in their mana costs.
         this.addAbility(
            new ConditionalAnyColorManaAbility(new TapSourceCost(),xValue,null,new HelgaSkittishSeerManaBuilder(),true)
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
    public ConditionalMana build(Object... options) {
        return new HelgaSkittishSeerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast creature spells with mana value 4 or greater or creature spells with {X} in their mana costs.";
    }
}

class HelgaSkittishSeerConditionalMana extends ConditionalMana {

    HelgaSkittishSeerConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast creature spells with mana value 4 or greater or creature spells with {X} in their mana costs.";
        addCondition(new HelgaSkittishSeerManaCondition());
    }
}

class HelgaSkittishSeerManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID manaProducer, Cost costToPay) {
        if (super.apply(game, source)) {
            MageObject object = game.getObject(source);
            if (object != null && (object.getManaValue() >= 4 || object.getManaCost().containsX())) {
                return true;
            }
        }
        return false;
    }
}