package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCircleOfLoyalty extends CardImpl {

    private static final FilterSpell filterLegendary = new FilterSpell("a legendary spell");

    static {
        filterLegendary.add(SuperType.LEGENDARY.getPredicate());
    }

    static final FilterControlledPermanent filterKnight = new FilterControlledPermanent("Knight you control");

    static {
        filterKnight.add(SubType.KNIGHT.getPredicate());
    }

    public TheCircleOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);

        // This spell costs {1} less to cast for each Knight you control.
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filterKnight);
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(new ValueHint("Knight you control", xValue)));

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)
        ));

        // Whenever you cast a legendary spell, create a 2/2 white Knight creature token with vigilance.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new KnightToken()), filterLegendary, false
        ));

        // {3}{W}, {T}: Create a 2/2 white Knight creature token with vigilance.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new KnightToken()), new ManaCostsImpl<>("{3}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheCircleOfLoyalty(final TheCircleOfLoyalty card) {
        super(card);
    }

    @Override
    public TheCircleOfLoyalty copy() {
        return new TheCircleOfLoyalty(this);
    }
}