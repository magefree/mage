package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class SatyaAetherfluxGenius extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
            new FilterControlledCreaturePermanent("other target nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SatyaAetherfluxGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Satya, Aetherflux Genius attacks, create a tapped and attacking token that's a copy of up to one other target nontoken creature you control. You get {E}{E}. At the beginning of the next end step, sacrifice that token unless you pay an amount of {E} equal to its mana value.
        Ability ability = new AttacksTriggeredAbility(new SatyaAetherfluxGeniusEffect());
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private SatyaAetherfluxGenius(final SatyaAetherfluxGenius card) {
        super(card);
    }

    @Override
    public SatyaAetherfluxGenius copy() {
        return new SatyaAetherfluxGenius(this);
    }
}

class SatyaAetherfluxGeniusEffect extends OneShotEffect {

    SatyaAetherfluxGeniusEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a tapped and attacking token that's a copy of up to one other target nontoken creature you control. "
                + "You get {E}{E}. At the beginning of the next end step, sacrifice that token unless you pay an amount of {E} equal to its mana value";
    }

    private SatyaAetherfluxGeniusEffect(final SatyaAetherfluxGeniusEffect effect) {
        super(effect);
    }

    @Override
    public SatyaAetherfluxGeniusEffect copy() {
        return new SatyaAetherfluxGeniusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect createEffect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, false, 1, true, true
        );
        createEffect.setTargetPointer(getTargetPointer().copy())
                .apply(game, source);
        new GetEnergyCountersControllerEffect(2).apply(game, source);
        OneShotEffect effect = new SatyaAetherfluxGeniusSacrificeEffect();
        effect.setTargetPointer(new FixedTargets(createEffect.getAddedPermanents(), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }

}

class SatyaAetherfluxGeniusSacrificeEffect extends OneShotEffect {

    SatyaAetherfluxGeniusSacrificeEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice that token unless you pay an amount of {E} equal to its mana value";
    }

    private SatyaAetherfluxGeniusSacrificeEffect(final SatyaAetherfluxGeniusSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public SatyaAetherfluxGeniusSacrificeEffect copy() {
        return new SatyaAetherfluxGeniusSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        int mv = permanents
                .stream()
                .mapToInt(Permanent::getManaValue)
                .sum();
        OneShotEffect effect = new DoUnlessControllerPaysEffect(
                new SacrificeTargetEffect(
                        "sacrifice that token unless you pay an amount of {E} equal to its mana value", source.getControllerId()
                ), new PayEnergyCost(mv));
        return effect.setTargetPointer(getTargetPointer().copy())
                .apply(game, source);
    }

}
