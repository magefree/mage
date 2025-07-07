package mage.cards.a;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
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
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(7), new TapSourceCost());
        ability.addCost(new PayEnergyCost(50).setText("pay fifty {E}"));
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
        int amount = Optional
                .ofNullable(this.getValue("spellCast"))
                .map(Spell.class::cast)
                .map(Spell::getStackAbility)
                .map(Ability::getManaCostsToPay)
                .map(ManaCost::getUsedManaToPay)
                .map(Mana::count)
                .orElse(0);
        return amount > 0
                && Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(player -> player.addCounters(CounterType.ENERGY.createInstance(amount), player.getId(), source, game))
                .isPresent();
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
