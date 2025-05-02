package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Alien00Token;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class ReconCraftTheta extends CardImpl {

    public ReconCraftTheta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Recon Craft Theta enters the battlefield, create a 0/0 blue Alien creature token. Put a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReconCraftThetaEffect()));

        // Whenever Recon Craft Theta attacks, proliferate.
        this.addAbility(new AttacksTriggeredAbility(new ProliferateEffect()));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private ReconCraftTheta(final ReconCraftTheta card) {
        super(card);
    }

    @Override
    public ReconCraftTheta copy() {
        return new ReconCraftTheta(this);
    }
}

class ReconCraftThetaEffect extends OneShotEffect {

    ReconCraftThetaEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 0/0 blue Alien creature token. Put a +1/+1 counter on it.";
    }

    private ReconCraftThetaEffect(final ReconCraftThetaEffect effect) {
        super(effect);
    }

    @Override
    public ReconCraftThetaEffect copy() {
        return new ReconCraftThetaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect tokenEffect = new CreateTokenEffect(new Alien00Token());
        if (!tokenEffect.apply(game, source)) {
            return false;
        }
        List<Permanent> tokens = tokenEffect
                .getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setTargetPointer(new FixedTargets(tokens, game))
                .apply(game, source);
        return true;
    }
}