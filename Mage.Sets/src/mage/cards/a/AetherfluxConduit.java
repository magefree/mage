package mage.cards.a;

import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.CastFromHandWithoutPayingManaCostEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author sobiech
 */
public final class AetherfluxConduit extends CardImpl {

    public AetherfluxConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Whenever you cast a spell, you get an amount of {E} equal to the amount of mana spent to cast that spell.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AetherfluxConduitEffect(),false
        ));

        // {T}, Pay fifty {E}: Draw seven cards. You may cast any number of spells from your hand without paying their mana costs.
        final Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(7), new TapSourceCost());
        ability.addCost(new PayEnergyCost(50));
        ability.addEffect(new CastFromHandWithoutPayingManaCostEffect());
        this.addAbility(ability);
    }

    private AetherfluxConduit(final AetherfluxConduit card) {
        super(card);
    }

    @Override
    public AetherfluxConduit copy() {
        return new AetherfluxConduit(this);
    }
}
class AetherfluxConduitEffect extends OneShotEffect {

    AetherfluxConduitEffect() {
        super(Outcome.Benefit);
        this.staticText = "you get an amount of {E} <i>(energy counters)</i> equal to the amount of mana spent to cast that spell";
    }
    private AetherfluxConduitEffect(AetherfluxConduitEffect effect) {
        super(effect);
    }

    @Override
    public AetherfluxConduitEffect copy() {
        return new AetherfluxConduitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(this.getValue("spellCast"))
                .map(Spell.class::cast)
                .ifPresent(spell -> new GetEnergyCountersControllerEffect(spell.getManaValue()).apply(game, source));
        return true;
    }
}

