package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class MaturationChamber extends CardImpl {

    public MaturationChamber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // Whenever you cast an artifact spell, put a charge counter on this artifact.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
            StaticFilters.FILTER_SPELL_AN_ARTIFACT, false
        ));

        // {4}, {T}: Create a 1/1 colorless Borg artifact creature token for each charge counter on this artifact. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new CreateTokenEffect(
                new BorgToken(),
                new CountersSourceCount(CounterType.CHARGE)
            ),
            new GenericManaCost(4)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MaturationChamber(final MaturationChamber card) {
        super(card);
    }

    @Override
    public MaturationChamber copy() {
        return new MaturationChamber(this);
    }
}
