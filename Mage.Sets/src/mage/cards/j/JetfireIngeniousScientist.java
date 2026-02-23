package mage.cards.j;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.AdaptEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.PowerstoneToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JetfireIngeniousScientist extends TransformingDoubleFacedCard {

    public JetfireIngeniousScientist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{4}{U}",
                "Jetfire, Air Guardian",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "U");

        // Jetfire, Ingenious Scientist
        this.getLeftHalfCard().setPT(3, 4);

        // More Than Meets the Eye {3}{U}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{3}{U}"));

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Remove one or more +1/+1 counters from among artifacts you control: Target player adds that much {C}. This mana can't be spent to cast nonartifact spells. Convert Jetfire.
        Ability ability = new SimpleActivatedAbility(
                new JetfireIngeniousScientistEffect(),
                new RemoveVariableCountersTargetCost(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS,
                        CounterType.P1P1, "one or more", 1
                )
        );
        ability.addEffect(new TransformSourceEffect().setText("convert {this}"));
        ability.addTarget(new TargetPlayer());
        this.getLeftHalfCard().addAbility(ability);

        // Jetfire, Air Guardian
        this.getRightHalfCard().setPT(3, 4);

        // Living metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // {U}{U}{U}: Convert Jetfire, then adapt 3.
        Ability backAbility = new SimpleActivatedAbility(
                new TransformSourceEffect()
                        .setText("convert {this}"),
                new ManaCostsImpl<>("{U}{U}{U}")
        );
        backAbility.addEffect(new AdaptEffect(3).concatBy(", then"));
        this.getRightHalfCard().addAbility(backAbility);
    }

    private JetfireIngeniousScientist(final JetfireIngeniousScientist card) {
        super(card);
    }

    @Override
    public JetfireIngeniousScientist copy() {
        return new JetfireIngeniousScientist(this);
    }
}

class JetfireIngeniousScientistEffect extends OneShotEffect {

    JetfireIngeniousScientistEffect() {
        super(Outcome.Benefit);
        staticText = "target player adds that much {C}. This mana can't be spent to cast nonartifact spells";
    }

    private JetfireIngeniousScientistEffect(final JetfireIngeniousScientistEffect effect) {
        super(effect);
    }

    @Override
    public JetfireIngeniousScientistEffect copy() {
        return new JetfireIngeniousScientistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        int countersRemoved = CardUtil.castStream(
                source.getCosts().stream(), VariableCost.class
        ).mapToInt(VariableCost::getAmount).sum();
        if (player == null || countersRemoved < 1) {
            return false;
        }
        ConditionalManaBuilder manaBuilder = PowerstoneToken.makeBuilder();
        Mana mana = manaBuilder.setMana(Mana.ColorlessMana(countersRemoved), source, game).build();
        player.getManaPool().addMana(mana, game, source);
        return true;
    }
}
