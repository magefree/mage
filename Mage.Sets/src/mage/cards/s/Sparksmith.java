package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetAndYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class Sparksmith extends CardImpl {
    
    public Sparksmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Sparksmith deals X damage to target creature and X damage to you, where X is the number of Goblins on the battlefield.
        Ability ability = new SimpleActivatedAbility(new SparksmithEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Sparksmith(final Sparksmith card) {
        super(card);
    }

    @Override
    public Sparksmith copy() {
        return new Sparksmith(this);
    }
}

// too lazy to handle dynamic value properly in the common class
class SparksmithEffect extends OneShotEffect {


    private static final FilterPermanent filter = new FilterPermanent("Goblins on the battlefield");
    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    SparksmithEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals X damage to target creature and X damage to you, " +
                "where X is the number of Goblins on the battlefield";
    }

    private SparksmithEffect(final SparksmithEffect effect) {
        super(effect);
    }

    @Override
    public SparksmithEffect copy() {
        return new SparksmithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = xValue.calculate(game, source, this);
        return new DamageTargetAndYouEffect(amount, amount).apply(game, source);
    }
}
