package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HydaelynTheMothercrystal extends CardImpl {

    public HydaelynTheMothercrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.nightCard = true;
        this.color.setWhite(true);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Blessing of Light -- At the beginning of combat on your turn, put a +1/+1 counter on another target creature you control. Until your next turn, it gains indestructible. If that creature is legendary, draw a card.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("Until your next turn, it gains indestructible"));
        ability.addEffect(new HydaelynTheMothercrystalEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability.withFlavorWord("Blessing of Light"));
    }

    private HydaelynTheMothercrystal(final HydaelynTheMothercrystal card) {
        super(card);
    }

    @Override
    public HydaelynTheMothercrystal copy() {
        return new HydaelynTheMothercrystal(this);
    }
}

class HydaelynTheMothercrystalEffect extends OneShotEffect {

    HydaelynTheMothercrystalEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature is legendary, draw a card";
    }

    private HydaelynTheMothercrystalEffect(final HydaelynTheMothercrystalEffect effect) {
        super(effect);
    }

    @Override
    public HydaelynTheMothercrystalEffect copy() {
        return new HydaelynTheMothercrystalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .filter(permanent -> permanent.isLegendary(game))
                .isPresent()
                && Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(player -> player.drawCards(1, source, game) > 0)
                .isPresent();
    }
}
