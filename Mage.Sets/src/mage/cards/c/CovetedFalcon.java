package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
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
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

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

class CovetedFalconEffect extends ContinuousEffectImpl {

    private boolean firstControlChange = true;
    private List<UUID> permanentIds = new ArrayList<>();

    CovetedFalconEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.Benefit);
        staticText = "target opponent gains control of any number of target permanents you control. " +
                "Draw a card for each one they gained control of this way";
    }

    private CovetedFalconEffect(final CovetedFalconEffect effect) {
        super(effect);
        firstControlChange = effect.firstControlChange;
        permanentIds = effect.permanentIds;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            discard(); // controller no longer exists
            return false;
        }

        UUID controllingOpponentId = source.getFirstTarget();
        if (firstControlChange) {
            permanentIds.addAll(source.getTargets().get(1).getTargets());
        }

        int cardsToDraw = 0;
        ListIterator<UUID> permanentsIterator = permanentIds.listIterator(permanentIds.size());
        boolean oneTargetStillExists = false;
        while (permanentsIterator.hasPrevious()) {
            Permanent permanent = game.getPermanent(permanentsIterator.previous());
            if (permanent == null) {
                continue;
            }
            oneTargetStillExists = true;

            if (permanent.isControlledBy(controllingOpponentId)) {
                continue;
            }

            boolean controlChanged = permanent.changeControllerId(controllingOpponentId, game, source);
            if (firstControlChange) {
                if (controlChanged) {
                    cardsToDraw++;
                } else {
                    // If we couldn't gain control of target permanent on the first try, we shouldn't try again
                    // Can happen due to Guardian Beast
                    permanentsIterator.remove();
                }
            }
        }

        if (cardsToDraw > 0) {
            new DrawCardSourceControllerEffect(cardsToDraw).apply(game, source);
        }

        // When there's no targets for opponent to control or the effect's controller left the game, effect can be discarded
        if (!oneTargetStillExists || permanentIds.isEmpty() || !controller.isInGame()) {
            discard();
        }
        firstControlChange = false;
        return true;
    }

    @Override
    public CovetedFalconEffect copy() {
        return new CovetedFalconEffect(this);
    }
}
