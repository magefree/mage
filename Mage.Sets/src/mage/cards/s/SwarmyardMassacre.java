package mage.cards.s;

import java.util.UUID;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SquirrelToken;

/**
 *
 * @author Grath
 */
public final class SwarmyardMassacre extends CardImpl {

    static final FilterCreaturePermanent notSwarmyardFilter = new FilterCreaturePermanent("each creature that isn't an Insect, Rat, Spider, or Squirrel");
    static final FilterCreaturePermanent swarmyardFilter = new FilterCreaturePermanent("creature you control that's an Insect, Rat, Spider, or Squirrel");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(swarmyardFilter, -1);

    static {
        notSwarmyardFilter.add(Predicates.not(Predicates.or(
                SubType.INSECT.getPredicate(),
                SubType.RAT.getPredicate(),
                SubType.SPIDER.getPredicate(),
                SubType.SQUIRREL.getPredicate()
        )));
        swarmyardFilter.add(Predicates.or(
                SubType.INSECT.getPredicate(),
                SubType.RAT.getPredicate(),
                SubType.SPIDER.getPredicate(),
                SubType.SQUIRREL.getPredicate()
        ));
    }

    public SwarmyardMassacre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
        

        // Create two 1/1 green Squirrel creature tokens. Then each creature that isn't an Insect, Rat, Spider, or Squirrel gets -1/-1 until end of turn for each creature you control that's an Insect, Rat, Spider, or Squirrel.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SquirrelToken(), 2));
        this.getSpellAbility().addEffect(new BoostAllEffect(
                xValue, xValue, Duration.EndOfTurn, notSwarmyardFilter, false)
                .concatBy("Then"));
    }

    private SwarmyardMassacre(final SwarmyardMassacre card) {
        super(card);
    }

    @Override
    public SwarmyardMassacre copy() {
        return new SwarmyardMassacre(this);
    }
}
