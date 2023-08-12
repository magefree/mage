package mage.cards.j;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class JetfireIngeniousScientist extends CardImpl {

    public JetfireIngeniousScientist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.j.JetfireAirGuardian.class;

        // More Than Meets the Eye {3}{U}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{3}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

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
        this.addAbility(ability);
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
