package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PuPuUFO extends CardImpl {

    public PuPuUFO(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.subtype.add(SubType.ALIEN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {T}: You may put a land card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A), new TapSourceCost()
        ));

        // {3}: Until end of turn, this creature's base power becomes equal to the number of Towns you control.
        this.addAbility(new SimpleActivatedAbility(new PuPuUFOEffect(), new GenericManaCost(3)).addHint(PuPuUFOEffect.getHint()));
    }

    private PuPuUFO(final PuPuUFO card) {
        super(card);
    }

    @Override
    public PuPuUFO copy() {
        return new PuPuUFO(this);
    }
}

class PuPuUFOEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.TOWN);
    private static final Hint hint = new ValueHint("Towns you control", new PermanentsOnBattlefieldCount(filter));

    public static Hint getHint() {
        return hint;
    }

    PuPuUFOEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, {this}'s base power becomes equal to the number of Towns you control";
    }

    private PuPuUFOEffect(final PuPuUFOEffect effect) {
        super(effect);
    }

    @Override
    public PuPuUFOEffect copy() {
        return new PuPuUFOEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            return false;
        }
        game.addEffect(new SetBasePowerSourceEffect(
                game.getBattlefield().count(filter, source.getControllerId(), source, game), Duration.EndOfTurn
        ), source);
        return true;
    }
}
