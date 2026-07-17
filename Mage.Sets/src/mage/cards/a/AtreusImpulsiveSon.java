package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtreusImpulsiveSon extends CardImpl {

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);

    public AtreusImpulsiveSon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {3}, {T}: Draw a card for each experience counter you have, then discard a card. Atreus, Impulsive Son deals 2 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(xValue)
                .setText("draw a card for each experience counter you have"), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new DiscardControllerEffect(1).concatBy(", then"));
        ability.addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT));
        this.addAbility(ability);

        // Partner--Father & son
        this.addAbility(PartnerVariantType.FATHER_AND_SON.makeAbility());
    }

    private AtreusImpulsiveSon(final AtreusImpulsiveSon card) {
        super(card);
    }

    @Override
    public AtreusImpulsiveSon copy() {
        return new AtreusImpulsiveSon(this);
    }
}
