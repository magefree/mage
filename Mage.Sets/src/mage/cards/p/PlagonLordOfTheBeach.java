package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlagonLordOfTheBeach extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with toughness greater than its power");

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creatures you control with toughness greater than power", xValue);

    public PlagonLordOfTheBeach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.STARFISH);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Plagon, Lord of the Beach enters, draw a card for each creature you control with toughness greater than its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(xValue)).addHint(hint));

        // {W/U}: Target creature you control assigns combat damage equal to its toughness rather than its power this turn.
        Ability ability = new SimpleActivatedAbility(new CombatDamageByToughnessTargetEffect(), new ManaCostsImpl<>("{W/U}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private PlagonLordOfTheBeach(final PlagonLordOfTheBeach card) {
        super(card);
    }

    @Override
    public PlagonLordOfTheBeach copy() {
        return new PlagonLordOfTheBeach(this);
    }
}
