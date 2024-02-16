package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SentinelOfThePearlTrident extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("historic permanent you control");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public SentinelOfThePearlTrident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Sentinel of the Pearl Trident enters the battlefield, you may exile target historic permanent you control. If you do, return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new SentinelOfThePearlTridentEffect(), true);
        etbAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(etbAbility);
    }

    private SentinelOfThePearlTrident(final SentinelOfThePearlTrident card) {
        super(card);
    }

    @Override
    public SentinelOfThePearlTrident copy() {
        return new SentinelOfThePearlTrident(this);
    }
}

class SentinelOfThePearlTridentEffect extends OneShotEffect {

    private static final String effectText = "exile target historic permanent you control. "
            + "If you do, return that card to the battlefield under its owner's control"
            + " at the beginning of the next end step. <i>(Artifacts, legendaries, and Sagas are historic.)</i>";

    SentinelOfThePearlTridentEffect() {
        super(Outcome.Detriment);
        staticText = effectText;
    }

    private SentinelOfThePearlTridentEffect(final SentinelOfThePearlTridentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                int zcc = permanent.getZoneChangeCounter(game);
                controller.moveCards(permanent, Zone.EXILED, source, game);
                //create delayed triggered ability
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                effect.setTargetPointer(new FixedTarget(permanent.getId(), zcc + 1));
                AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public SentinelOfThePearlTridentEffect copy() {
        return new SentinelOfThePearlTridentEffect(this);
    }

}
