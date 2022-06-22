package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class KrovikanElementalist extends CardImpl {

    public KrovikanElementalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{R}: Target creature gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(1, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}{R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {U}{U}: Target creature you control gains flying until end of turn. Sacrifice it at the beginning of the next end step.
        ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(),
                Duration.EndOfTurn
        ), new ManaCostsImpl<>("{U}{U}"));
        ability.addEffect(new KrovikanElementalistEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private KrovikanElementalist(final KrovikanElementalist card) {
        super(card);
    }

    @Override
    public KrovikanElementalist copy() {
        return new KrovikanElementalist(this);
    }
}

class KrovikanElementalistEffect extends OneShotEffect {

    public KrovikanElementalistEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Sacrifice it at the beginning of the next end step";
    }

    public KrovikanElementalistEffect(final KrovikanElementalistEffect effect) {
        super(effect);
    }

    @Override
    public KrovikanElementalistEffect copy() {
        return new KrovikanElementalistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice it", source.getControllerId())
        )).apply(game, source);
    }
}
