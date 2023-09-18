package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CharmingPrince extends CardImpl {


    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public CharmingPrince(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Charming Prince enters the battlefield, choose one —
        // • Scry 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScryEffect(2));

        // • You gain 3 life.
        ability.addMode(new Mode(new GainLifeEffect(3)));

        // • Exile another target creature you own. Return it to the battlefield under your control at the beginning of the next end step.
        Mode mode = new Mode(new CharmingPrinceEffect());
        mode.addTarget(new TargetPermanent(filter));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private CharmingPrince(final CharmingPrince card) {
        super(card);
    }

    @Override
    public CharmingPrince copy() {
        return new CharmingPrince(this);
    }
}

class CharmingPrinceEffect extends OneShotEffect {

    CharmingPrinceEffect() {
        super(Outcome.Benefit);
        staticText = "Exile another target creature you own. " +
                "Return it to the battlefield under your control at the beginning of the next end step.";
    }

    private CharmingPrinceEffect(final CharmingPrinceEffect effect) {
        super(effect);
    }

    @Override
    public CharmingPrinceEffect copy() {
        return new CharmingPrinceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        //create delayed triggered ability
        Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
        effect.setText("Return it to the battlefield under your control at the beginning of the next end step");
        effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;

    }
}