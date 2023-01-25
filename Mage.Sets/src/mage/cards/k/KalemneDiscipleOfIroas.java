
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class KalemneDiscipleOfIroas extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a creature spell with mana value 5 or greater");

    static {
        filterSpell.add(CardType.CREATURE.getPredicate());
        filterSpell.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public KalemneDiscipleOfIroas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a creature spell with converted mana cost 5 or greater, you get an experience counter.
        Effect effect = new AddCountersControllerEffect(CounterType.EXPERIENCE.createInstance(1));
        effect.setText("you get an experience counter");
        Ability ability = new SpellCastControllerTriggeredAbility(effect, filterSpell, false);
        this.addAbility(ability);

        // Kalemne, Disciple of Iroas gets +1/+1 for each experience counter you have.
        DynamicValue value = new SourceControllerExperienceCountersCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)));
    }

    private KalemneDiscipleOfIroas(final KalemneDiscipleOfIroas card) {
        super(card);
    }

    @Override
    public KalemneDiscipleOfIroas copy() {
        return new KalemneDiscipleOfIroas(this);
    }
}

class SourceControllerExperienceCountersCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            amount = player.getCounters().getCount(CounterType.EXPERIENCE);
        }
        return amount;
    }

    @Override
    public SourceControllerExperienceCountersCount copy() {
        return new SourceControllerExperienceCountersCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "experience counter you have";
    }
}
