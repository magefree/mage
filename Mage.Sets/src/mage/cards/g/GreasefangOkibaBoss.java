package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class GreasefangOkibaBoss extends CardImpl {

    private static final FilterCard filter = new FilterCard("Vehicle card from your graveyard");

    static {
        filter.add(SubType.VEHICLE.getPredicate());
    }

    public GreasefangOkibaBoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, return target Vehicle card from your graveyard to the battlefield. It gains haste. Return it your hand at beginning of the next end step.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GreasefangOkibaBossEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private GreasefangOkibaBoss(final GreasefangOkibaBoss card) {
        super(card);
    }

    @Override
    public GreasefangOkibaBoss copy() {
        return new GreasefangOkibaBoss(this);
    }
}

class GreasefangOkibaBossEffect extends OneShotEffect {

    public GreasefangOkibaBossEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return target Vehicle card from your graveyard to the battlefield. It gains haste. Return it to its owner's hand at the beginning of your next end step";
    }

    private GreasefangOkibaBossEffect(final GreasefangOkibaBossEffect effect) {
        super(effect);
    }

    @Override
    public GreasefangOkibaBossEffect copy() {
        return new GreasefangOkibaBossEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null || card == null || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            hasteEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(hasteEffect, source);
            Effect bounceEffect = new ReturnToHandTargetEffect().setText("return it to your hand");
            bounceEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(bounceEffect), source);
        }
        return true;
    }
}
