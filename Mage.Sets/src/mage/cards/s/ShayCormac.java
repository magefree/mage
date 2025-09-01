package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShayCormac extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("a spell or ability you control");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("a creature with a bounty counter on it");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(CounterType.BOUNTY.getPredicate());
    }

    public ShayCormac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Permanents your opponents control lose hexproof, indestructible, protection, shroud, and ward until end of turn.
        this.addAbility(new SimpleActivatedAbility(new ShayCormacEffect(), new GenericManaCost(1)));

        // Whenever a creature an opponent controls becomes the target of a spell or ability you control, put a bounty counter on that creature.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(
                new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()),
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE, filter
        ));

        // Whenever a creature with a bounty counter on it dies, put two +1/+1 counters on Shay Cormac.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter2
        ));
    }

    private ShayCormac(final ShayCormac card) {
        super(card);
    }

    @Override
    public ShayCormac copy() {
        return new ShayCormac(this);
    }
}

class ShayCormacEffect extends ContinuousEffectImpl {

    ShayCormacEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "permanents your opponents control lose hexproof, " +
                "indestructible, protection, shroud, and ward until end of turn";
    }

    private ShayCormacEffect(final ShayCormacEffect effect) {
        super(effect);
    }

    @Override
    public ShayCormacEffect copy() {
        return new ShayCormacEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_OPPONENTS_PERMANENT,
                        source.getControllerId(), source, game
                )
                .stream()
                .map(permanent -> new MageObjectReference(permanent, game))
                .forEach(affectedObjectList::add);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (MageObjectReference mor : affectedObjectList) {
            Permanent permanent = mor.getPermanent(game);
            if (permanent == null) {
                continue;
            }
            // I know the removeIf is deprecated but I can't see any reason not to use it based on what the removeAbility method actually does
            permanent.getAbilities(game).removeIf(HexproofBaseAbility.class::isInstance);
            permanent.removeAbility(IndestructibleAbility.getInstance(), source.getSourceId(), game);
            permanent.getAbilities(game).removeIf(ProtectionAbility.class::isInstance);
            permanent.removeAbility(ShroudAbility.getInstance(), source.getSourceId(), game);
            permanent.getAbilities(game).removeIf(WardAbility.class::isInstance);
        }
        return true;
    }
}
