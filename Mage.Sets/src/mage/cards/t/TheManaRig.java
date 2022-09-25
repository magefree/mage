package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TheManaRig extends CardImpl {

    public TheManaRig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        addSuperType(SuperType.LEGENDARY);

        // Whenever you cast a multicolored spell, create a tapped Powerstone token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new PowerstoneToken(), 1, true, false), StaticFilters.FILTER_SPELL_A_MULTICOLORED, false
        ));

        // {X}{X}{X}, {T}: Look at the top X cards of your library. Put up to two of them into your hand and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                ManacostVariableValue.REGULAR, 2, PutCards.HAND, PutCards.BOTTOM_RANDOM, true
                ).setText("Look at the top X cards of your library. " +
                "Put up to two of them into your hand and the rest on the bottom of your library in a random order"),
                new ManaCostsImpl<>("{X}{X}{X}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheManaRig(final TheManaRig card) {
        super(card);
    }

    @Override
    public TheManaRig copy() {
        return new TheManaRig(this);
    }
}
