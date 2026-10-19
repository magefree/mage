package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureRandomCreatureEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

public final class CosmicSovereign extends CardImpl {

    public CosmicSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        // {R}: This creature gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
        // At the beginning of combat on your turn, conjure a random creature card with mana value X onto the battlefield, where X is this creature's power. That creature gains haste. Exile it at the beginning of the next end step.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new CosmicSovereignEffect()));
    }

    private CosmicSovereign(final CosmicSovereign card) {
        super(card);
    }

    @Override
    public CosmicSovereign copy() {
        return new CosmicSovereign(this);
    }
}

class CosmicSovereignEffect extends OneShotEffect {

    CosmicSovereignEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "conjure a random creature card with mana value X onto the battlefield, "
                + "where X is {this}'s power. That creature gains haste. Exile it at the beginning of the next end step";
    }

    private CosmicSovereignEffect(final CosmicSovereignEffect effect) {
        super(effect);
    }

    @Override
    public CosmicSovereignEffect copy() {
        return new CosmicSovereignEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return false;
        }
        Card card = ConjureRandomCreatureEffect.conjure(
                sourcePermanent.getPower().getValue(), Zone.BATTLEFIELD, game, source
        );
        Permanent permanent = card == null ? null : CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ExileTargetEffect().setTargetPointer(new FixedTarget(permanent, game))
        ), source);
        return true;
    }
}
