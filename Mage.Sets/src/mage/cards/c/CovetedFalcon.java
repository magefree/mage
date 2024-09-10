package mage.cards.c;

import java.util.List;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisguiseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Cguy7777
 */
public final class CovetedFalcon extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("permanent you own but don't control");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public CovetedFalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Coveted Falcon attacks, gain control of target permanent you own but don't control.
        Ability attacksTriggeredAbility = new AttacksTriggeredAbility(new GainControlTargetEffect(Duration.Custom));
        attacksTriggeredAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(attacksTriggeredAbility);

        // Disguise {1}{U}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{1}{U}")));

        // When Coveted Falcon is turned face up, target opponent gains control of any number of target permanents you control.
        // Draw a card for each one they gained control of this way.
        Ability turnedFaceUpTriggeredAbility = new TurnedFaceUpSourceTriggeredAbility(new CovetedFalconEffect());
        turnedFaceUpTriggeredAbility.addTarget(new TargetOpponent());
        turnedFaceUpTriggeredAbility.addTarget(
                new TargetControlledPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_PERMANENT, false));
        this.addAbility(turnedFaceUpTriggeredAbility);
    }

    private CovetedFalcon(final CovetedFalcon card) {
        super(card);
    }

    @Override
    public CovetedFalcon copy() {
        return new CovetedFalcon(this);
    }
}

class CovetedFalconEffect extends OneShotEffect {

    CovetedFalconEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent gains control of any number of target permanents you control. " +
                "Draw a card for each one they gained control of this way";
    }

    private CovetedFalconEffect(final CovetedFalconEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetOpponentId = source.getFirstTarget();
        List<UUID> targetPermanentIds = source.getTargets().get(1).getTargets();
        for (UUID permanentId : targetPermanentIds) {
            game.addEffect(
                    new GainControlTargetEffect(Duration.Custom, true, targetOpponentId)
                            .setTargetPointer(new FixedTarget(permanentId, game)),
                    source);
        }

        game.processAction();

        int cardsToDraw = 0;
        for (UUID permanentId : targetPermanentIds) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.isControlledBy(targetOpponentId)) {
                cardsToDraw++;
            }
        }

        new DrawCardSourceControllerEffect(cardsToDraw).apply(game, source);
        return true;
    }

    @Override
    public CovetedFalconEffect copy() {
        return new CovetedFalconEffect(this);
    }
}
