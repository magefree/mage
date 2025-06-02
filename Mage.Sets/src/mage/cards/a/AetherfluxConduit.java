package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author sobiech
 */
public final class AetherfluxConduit extends CardImpl {

    public AetherfluxConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Whenever you cast a spell, you get an amount of {E} equal to the amount of mana spent to cast that spell.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AetherfluxConduitManaEffect(), false));

        // {T}, Pay fifty {E}: Draw seven cards. You may cast any number of spells from your hand without paying their mana costs.
        final Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(7), new TapSourceCost());
        ability.addCost(new PayEnergyCost(50).setText("Pay fifty {E}"));
        ability.addEffect(new AetherfluxConduitCastEffect());
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

class AetherfluxConduitManaEffect extends OneShotEffect {

    AetherfluxConduitManaEffect() {
        super(Outcome.Benefit);
        this.staticText = "you get an amount of {E} <i>(energy counters)</i> equal to the amount of mana spent to cast that spell";
    }

    private AetherfluxConduitManaEffect(AetherfluxConduitManaEffect effect) {
        super(effect);
    }

    @Override
    public AetherfluxConduitManaEffect copy() {
        return new AetherfluxConduitManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(this.getValue("spellCast"))
                .map(Spell.class::cast)
                .ifPresent(spell -> new GetEnergyCountersControllerEffect(spell.getManaValue()).apply(game, source));
        return true;
    }
}

class AetherfluxConduitCastEffect extends OneShotEffect {

    AetherfluxConduitCastEffect() {
        super(Outcome.Benefit);
        staticText = "You may cast any number of spells from your hand without paying their mana costs";
    }

    private AetherfluxConduitCastEffect(final AetherfluxConduitCastEffect effect) {
        super(effect);
    }

    @Override
    public AetherfluxConduitCastEffect copy() {
        return new AetherfluxConduitCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CardUtil.castMultipleWithAttributeForFree(
                player, source, game, new CardsImpl(player.getHand()), StaticFilters.FILTER_CARD
        );
        return true;
    }
}
