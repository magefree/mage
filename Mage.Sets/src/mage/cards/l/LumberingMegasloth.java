package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class LumberingMegasloth extends CardImpl {

    public LumberingMegasloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}{G}{G}");

        this.subtype.add(SubType.SLOTH);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        // This spell costs {1} less to cast for each counter among players and permanents.
        this.addAbility(
                new SimpleStaticAbility(Zone.ALL,
                        new SpellCostReductionForEachSourceEffect(1, LumberingMegaslothValue.instance)
                ).addHint(LumberingMegaslothValue.getHint())
        );

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lumbering Megasloth enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private LumberingMegasloth(final LumberingMegasloth card) {
        super(card);
    }

    @Override
    public LumberingMegasloth copy() {
        return new LumberingMegasloth(this);
    }
}

enum LumberingMegaslothValue implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Number of Counters:", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int onPermanents = game.getBattlefield()
                .getActivePermanents(sourceAbility.getControllerId(), game)
                .stream()
                .filter(Objects::nonNull)
                .map(perm -> perm.getCounters(game))
                .flatMap(counters -> counters.values().stream())
                .mapToInt(counter -> counter.getCount())
                .sum();
        int onPlayers = game.getState()
                .getPlayersInRange(sourceAbility.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(player -> player.getCounters())
                .flatMap(counters -> counters.values().stream())
                .mapToInt(counter -> counter.getCount())
                .sum();
        return onPermanents + onPlayers;
    }

    @Override
    public LumberingMegaslothValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "counters among players and permanents";
    }
}