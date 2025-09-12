package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileTopCardPlayUntilExileAnotherEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class UnstableAmulet extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public UnstableAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // When Unstable Amulet enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Whenever you cast a spell from anywhere other than your hand, Unstable Amulet deals 1 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT),
                filter, false
        ));

        // {T}, Pay {E}{E}: Exile the top card of your library. You may play it until you exile another card with Unstable Amulet.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopCardPlayUntilExileAnotherEffect("it"),
                new TapSourceCost()
        );
        ability.addCost(new PayEnergyCost(2));
        this.addAbility(ability);
    }

    private UnstableAmulet(final UnstableAmulet card) {
        super(card);
    }

    @Override
    public UnstableAmulet copy() {
        return new UnstableAmulet(this);
    }
}