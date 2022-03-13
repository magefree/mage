package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlaneboundAccomplice extends CardImpl {

    public PlaneboundAccomplice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {R}: You may put a planeswalker card from your hand onto the battlefield. Sacrifice it at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(new PlaneboundAccompliceEffect(), new ManaCostsImpl("{R}")));
    }

    private PlaneboundAccomplice(final PlaneboundAccomplice card) {
        super(card);
    }

    @Override
    public PlaneboundAccomplice copy() {
        return new PlaneboundAccomplice(this);
    }
}

class PlaneboundAccompliceEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPlaneswalkerCard();

    PlaneboundAccompliceEffect() {
        super(Outcome.Benefit);
        staticText = "You may put a planeswalker card from your hand onto the battlefield. " +
                "Sacrifice it at the beginning of the next end step.";
    }

    private PlaneboundAccompliceEffect(final PlaneboundAccompliceEffect effect) {
        super(effect);
    }

    @Override
    public PlaneboundAccompliceEffect copy() {
        return new PlaneboundAccompliceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (!controller.chooseUse(Outcome.PutCardInPlay, "Put a planeswalker card from your hand onto the battlefield?", source, game)) {
            return true;
        }
        TargetCardInHand target = new TargetCardInHand(filter);
        if (!controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        if (!controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return true;
        }
        SacrificeTargetEffect sacrificeEffect
                = new SacrificeTargetEffect("sacrifice " + card.getName(), source.getControllerId());
        sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}