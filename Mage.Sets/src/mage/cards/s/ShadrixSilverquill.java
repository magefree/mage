package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InklingToken;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadrixSilverquill extends CardImpl {

    private static final FilterPlayer filter0 = new FilterPlayer("a different player");
    private static final FilterPlayer filter1 = new FilterPlayer();
    private static final FilterPlayer filter2 = new FilterPlayer();
    private static final FilterPlayer filter3 = new FilterPlayer();

    static {
        filter1.add(new AnotherTargetPredicate(1, true));
        filter2.add(new AnotherTargetPredicate(2, true));
        filter3.add(new AnotherTargetPredicate(3, true));
    }

    public ShadrixSilverquill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // At the beginning of combat on your turn, you may choose two. Each mode must target a different player.
        Ability ability = new BeginningOfCombatTriggeredAbility(null, TargetController.YOU, false);
        ability.getModes().setMinModes(2);
        ability.getModes().setMaxModes(2);
        ability.getModes().setMaxModesFilter(filter0);
        ability.getModes().setMayChooseNone(true);

        // • Target player creates a 2/1 white and black Inkling creature token with flying.
        ability.addEffect(new CreateTokenTargetEffect(new InklingToken()));
        ability.addTarget(new TargetPlayer(filter1).setTargetTag(1).withChooseHint("to create a token"));

        // • Target player draws a card and loses 1 life.
        ability.addMode(new Mode(new DrawCardTargetEffect(1))
                .addEffect(new LoseLifeTargetEffect(1).setText("and loses 1 life"))
                .addTarget(new TargetPlayer(filter2).setTargetTag(2).withChooseHint("to draw a card and lose 1 life")));

        // • Target player puts a +1/+1 counter on each creature they control.
        ability.addMode(new Mode(new ShadrixSilverquillEffect())
                .addTarget(new TargetPlayer(filter3).setTargetTag(3).withChooseHint("to put a counter on each creature")));
        this.addAbility(ability);
    }

    private ShadrixSilverquill(final ShadrixSilverquill card) {
        super(card);
    }

    @Override
    public ShadrixSilverquill copy() {
        return new ShadrixSilverquill(this);
    }
}

class ShadrixSilverquillEffect extends OneShotEffect {

    ShadrixSilverquillEffect() {
        super(Outcome.Benefit);
        staticText = "target player puts a +1/+1 counter on each creature they control";
    }

    private ShadrixSilverquillEffect(final ShadrixSilverquillEffect effect) {
        super(effect);
    }

    @Override
    public ShadrixSilverquillEffect copy() {
        return new ShadrixSilverquillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getPlayer(source.getFirstTarget()) == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getFirstTarget(), source, game
        )) {
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getFirstTarget(), source, game);
        }
        return true;
    }
}
