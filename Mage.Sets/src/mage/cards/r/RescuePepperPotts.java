package mage.cards.r;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class RescuePepperPotts extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("other target artifact or creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
    }

    public RescuePepperPotts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Rescue enters, return up to one other target artifact or creature you control to its owner's hand. If it was an artifact, put a +1/+1 counter on Rescue.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RescuePepperPottsEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private RescuePepperPotts(final RescuePepperPotts card) {
        super(card);
    }

    @Override
    public RescuePepperPotts copy() {
        return new RescuePepperPotts(this);
    }
}

class RescuePepperPottsEffect extends OneShotEffect {

    RescuePepperPottsEffect() {
        super(Outcome.Benefit);
        staticText = "return up to one other target artifact or creature you control " +
            "to its owner's hand. If it was an artifact, put a +1/+1 counter on {this}";
    }

    private RescuePepperPottsEffect(final RescuePepperPottsEffect effect) {
        super(effect);
    }

    @Override
    public RescuePepperPottsEffect copy() {
        return new RescuePepperPottsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.HAND, source, game);
        if (permanent.isArtifact(game)) {
            Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(p -> p.addCounters(CounterType.P1P1.createInstance(), source, game));
        }
        return true;
    }
}
