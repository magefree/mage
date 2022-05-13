package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ShrewdHatchling extends CardImpl {

    private static final FilterSpell filterBlueSpell = new FilterSpell("a blue spell");
    private static final FilterSpell filterRedSpell = new FilterSpell("a red spell");

    static {
        filterBlueSpell.add(new ColorPredicate(ObjectColor.BLUE));
        filterRedSpell.add(new ColorPredicate(ObjectColor.RED));
    }

    public ShrewdHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U/R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Shrewd Hatchling enters the battlefield with four -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.M1M1.createInstance(4)
        ), "with four -1/-1 counters on it"));

        // {U/R}: Target creature can't block Shrewd Hatchling this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{U/R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever you cast a blue spell, remove a -1/-1 counter from Shrewd Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filterBlueSpell, false
        ));

        // Whenever you cast a red spell, remove a -1/-1 counter from Shrewd Hatchling.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filterRedSpell, false
        ));

    }

    private ShrewdHatchling(final ShrewdHatchling card) {
        super(card);
    }

    @Override
    public ShrewdHatchling copy() {
        return new ShrewdHatchling(this);
    }
}
