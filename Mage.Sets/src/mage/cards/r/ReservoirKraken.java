package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FishToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class ReservoirKraken extends CardImpl {

    public ReservoirKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // At the beginning of each combat, if Reservoir Kraken is untapped, any opponent may tap an untapped creature they control. If they do, tap Reservoir Kraken and create a 1/1 blue Fish creature token with "This creature can't be blocked."
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new ReservoirKrakenEffect(), TargetController.ANY, false),
                SourceTappedCondition.UNTAPPED,
                "At the beginning of each combat, if {this} is untapped, any opponent may tap an untapped creature they control. If they do, tap {this} and create a 1/1 blue Fish creature token with \"This creature can't be blocked.\""
        ));
    }

    private ReservoirKraken(final ReservoirKraken card) {
        super(card);
    }

    @Override
    public ReservoirKraken copy() {
        return new ReservoirKraken(this);
    }
}

class ReservoirKrakenEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public ReservoirKrakenEffect() {
        super(Outcome.Tap);
        this.staticText = "any opponent may tap an untapped creature they control. If they do, tap {this} and create a 1/1 blue Fish creature token with \"This creature can't be blocked.\"";
    }

    private ReservoirKrakenEffect(final ReservoirKrakenEffect effect) {
        super(effect);
    }

    @Override
    public ReservoirKrakenEffect copy() {
        return new ReservoirKrakenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean opponentTapped = false;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1, 1, filter, true);
                if (target.canChoose(opponentId, source, game) && opponent.chooseUse(Outcome.AIDontUseIt, "Tap an untapped creature you control?", source, game)) {
                    opponent.chooseTarget(Outcome.Tap, target, source, game);
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null && permanent.tap(source, game)) {
                        opponentTapped = true;
                    }
                }
            }
        }
        if (opponentTapped) {
            Permanent kraken = source.getSourcePermanentIfItStillExists(game);
            if (kraken != null) {
                kraken.tap(source, game);
            }
            new FishToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
