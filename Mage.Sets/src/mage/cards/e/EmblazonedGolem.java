package mage.cards.e;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetKickerXValue;
import mage.abilities.dynamicvalue.common.SunburstCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author sprangg
 */
public final class EmblazonedGolem extends CardImpl {

    public EmblazonedGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Kicker {X}
        this.addAbility(new KickerAbility("{X}"));
        
        // Spend only colored mana on X. No more than one mana of each color may be spent this way.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("Spend only colored mana on X. No more than one mana of each color may be spent this way."))
        );
        
        // If Emblazoned Golem was kicked, it enters the battlefield with X +1/+1 counters on it.                
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(),
                EmblazonedGolemKickerValue.instance, false),
                KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield with X +1/+1 counters on it.", ""));
    }

    private EmblazonedGolem(final EmblazonedGolem card) {
        super(card);
    }

    @Override
    public EmblazonedGolem copy() {
        return new EmblazonedGolem(this);
    }
}

enum EmblazonedGolemKickerValue implements DynamicValue {
    instance;
    
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int sunburst = SunburstCount.instance.calculate(game, sourceAbility, effect); // the amount of different colors spent on casting this
        int kickerX = GetKickerXValue.instance.calculate(game, sourceAbility, effect);
        
        Spell spell = game.getSpellOrLKIStack(sourceAbility.getSourceId());
        if (spell == null || spell.getSpellAbility() == null) {
            return 0;
        }    
        
        Mana mana = spell.getSpellAbility().getManaCostsToPay().getUsedManaToPay();
        int costReduction = Math.max(0, 2 + kickerX - mana.count()); // if less than 2+X mana was paid for this spell, its cost was reduced
        
        return Math.min(sunburst + costReduction, kickerX); // the returned value is the sum of sunburst + cost reduction, but it can't be higher than the kicker's X value
    }

    @Override
    public EmblazonedGolemKickerValue copy() {
        return this;
    }
    
    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}