package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightPile extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creatures with defender you control");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creatures with defender you control", xValue);

    public BlightPile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}{B}, {T}: Each opponent loses X life, where X is the number of creatures with defender you control.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(xValue), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(hint));
    }

    private BlightPile(final BlightPile card) {
        super(card);
    }

    @Override
    public BlightPile copy() {
        return new BlightPile(this);
    }
}
