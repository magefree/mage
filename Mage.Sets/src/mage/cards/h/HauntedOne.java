package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntedOne extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(HauntedOnePredicate.instance);
    }

    public HauntedOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever this creature becomes tapped, it and other creatures you control that share a creature type with it each get +2/+0 and gain undying until end of turn."
        Ability ability = new BecomesTappedSourceTriggeredAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn).setText("it")
        ).setTriggerPhrase("Whenever this creature becomes tapped, ");
        ability.addEffect(new GainAbilitySourceEffect(
                new UndyingAbility(), Duration.EndOfTurn
        ).setText("and other creatures you control that share a creature type with it"));
        ability.addEffect(new BoostAllEffect(
                2, 0, Duration.EndOfTurn, filter, true
        ).setText("each get +2/+0"));
        ability.addEffect(new GainAbilityAllEffect(
                new UndyingAbility(), Duration.EndOfTurn, filter, true
        ).setText("and gain undying until end of turn"));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private HauntedOne(final HauntedOne card) {
        super(card);
    }

    @Override
    public HauntedOne copy() {
        return new HauntedOne(this);
    }
}

enum HauntedOnePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional.of(input.getSource().getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.shareCreatureTypes(game, input.getObject()))
                .orElse(false);
    }
}
