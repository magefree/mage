package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RedMutantToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class SplinterAndLeoFatherAndSon extends CardImpl {

    private static final FilterPlayer filter0 = new FilterPlayer("a different player");
    private static final FilterPlayer filter1 = new FilterPlayer();
    private static final FilterPlayer filter2 = new FilterPlayer();

    static {
        filter1.add(new AnotherTargetPredicate(1, true));
        filter2.add(new AnotherTargetPredicate(2, true));
    }

    public SplinterAndLeoFatherAndSon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Splinter & Leo enter, choose one or both. Each mode must target a different player.
        // * Target player creates a 2/2 red Mutant creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new RedMutantToken()));
        ability.addTarget(new TargetPlayer(filter1).withChooseHint("creates a 2/2 red Mutant creature token"));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(2);
        ability.getModes().setLimitUsageByOnce(false);
        ability.getModes().setMaxModesFilter(filter0);

        // * Put a +1/+1 counter on each other creature target player controls.
        ability.addMode(new Mode(new SplinterAndLeoFatherAndSonEffect())
            .addTarget(new TargetPlayer(filter2).withChooseHint("put a +1/+1 counter on each other creature they control")));
        this.addAbility(ability);
    }

    private SplinterAndLeoFatherAndSon(final SplinterAndLeoFatherAndSon card) {
        super(card);
    }

    @Override
    public SplinterAndLeoFatherAndSon copy() {
        return new SplinterAndLeoFatherAndSon(this);
    }
}

class SplinterAndLeoFatherAndSonEffect extends OneShotEffect {

    SplinterAndLeoFatherAndSonEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on each other creature target player controls";
    }

    private SplinterAndLeoFatherAndSonEffect(final SplinterAndLeoFatherAndSonEffect effect) {
        super(effect);
    }

    @Override
    public SplinterAndLeoFatherAndSonEffect copy() {
        return new SplinterAndLeoFatherAndSonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            if (permanent.isCreature() && !permanent.getId().equals(source.getSourceId())) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        return true;
    }
}
