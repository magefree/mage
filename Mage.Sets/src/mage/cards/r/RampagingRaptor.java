package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.ProtectorIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RampagingRaptor extends CardImpl {

    public RampagingRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {2}{R}: Rampaging Raptor gets +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}")
        ));

        // Whenever Rampaging Raptor deals combat damage to an opponent, it deals that much damage to target planeswalker that player controls or battle that player protects.
        Ability ability = new DealsDamageToOpponentTriggeredAbility(new DamageTargetEffect(SavedDamageValue.MUCH)
                .withTargetDescription("target planeswalker that player controls or battle that player protects"), false, true, true);
        ability.setTargetAdjuster(RampagingRaptorTargetAdjuster.instance);
        this.addAbility(ability);
    }

    private RampagingRaptor(final RampagingRaptor card) {
        super(card);
    }

    @Override
    public RampagingRaptor copy() {
        return new RampagingRaptor(this);
    }
}

enum RampagingRaptorTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID opponentId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        Player opponent = game.getPlayer(opponentId);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());
        if (opponent == null) {
            return;
        }
        FilterPermanent filter = new FilterPermanent(
                "planeswalker " + opponent.getLogName() + " controls " +
                        "or battle " + opponent.getLogName() + " protects"
        );
        filter.add(Predicates.or(
                Predicates.and(
                        CardType.PLANESWALKER.getPredicate(),
                        new ControllerIdPredicate(opponent.getId())
                ),
                Predicates.and(
                        CardType.BATTLE.getPredicate(),
                        new ProtectorIdPredicate(opponent.getId())
                )
        ));
        ability.addTarget(new TargetPermanent(filter));
    }
}
