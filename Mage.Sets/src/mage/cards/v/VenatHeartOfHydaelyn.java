package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenatHeartOfHydaelyn extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("a legendary spell");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public VenatHeartOfHydaelyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDER, SubType.WIZARD}, "{1}{W}{W}",
                "Hydaelyn, the Mothercrystal",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "W"
        );

        // Venat, Heart of Hydaelyn
        this.getLeftHalfCard().setPT(3, 3);

        // Whenever you cast a legendary spell, draw a card. This ability triggers only once each turn.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ).setTriggersLimitEachTurn(1));

        // Hero's Sundering -- {7}, {T}: Exile target nonland permanent. Transform Venat. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new ExileTargetEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new TransformSourceEffect());
        ability.addTarget(new TargetNonlandPermanent());
        this.getLeftHalfCard().addAbility(ability.withFlavorWord("Hero's Sundering"));

        // Hydaelyn, the Mothercrystal
        this.getRightHalfCard().setPT(4, 4);

        // Indestructible
        this.getRightHalfCard().addAbility(IndestructibleAbility.getInstance());

        // Blessing of Light -- At the beginning of combat on your turn, put a +1/+1 counter on another target creature you control. Until your next turn, it gains indestructible. If that creature is legendary, draw a card.
        Ability ability2 = new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability2.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("Until your next turn, it gains indestructible"));
        ability2.addEffect(new HydaelynTheMothercrystalEffect());
        ability2.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.getRightHalfCard().addAbility(ability2.withFlavorWord("Blessing of Light"));
    }

    private VenatHeartOfHydaelyn(final VenatHeartOfHydaelyn card) {
        super(card);
    }

    @Override
    public VenatHeartOfHydaelyn copy() {
        return new VenatHeartOfHydaelyn(this);
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
