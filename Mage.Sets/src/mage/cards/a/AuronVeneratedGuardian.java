package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuronVeneratedGuardian extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature defending player controls with power less than {this}'s power");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
        filter.add(AuronVeneratedGuardianPredicate.instance);
    }

    public AuronVeneratedGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Shooting Star -- Whenever Auron attacks, put a +1/+1 counter on it. When you do, exile target creature defending player controls with power less than Auron's power until Auron leaves the battlefield.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ExileUntilSourceLeavesEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new PutCountersSourceCost(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on it"), "", false
        )).withFlavorWord("Shooting Star"));
    }

    private AuronVeneratedGuardian(final AuronVeneratedGuardian card) {
        super(card);
    }

    @Override
    public AuronVeneratedGuardian copy() {
        return new AuronVeneratedGuardian(this);
    }
}

enum AuronVeneratedGuardianPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(x -> input.getObject().getPower().getValue() < x)
                .orElse(false);
    }
}
