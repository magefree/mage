package mage.cards.v;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class VindictiveTriumph extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public VindictiveTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}{B}");

        // Exile target creature or planeswalker. If that permanent's mana value was 3 or less, return it to the battlefield tapped under your control. Exile it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new VindictiveTriumphEffect(), condition,
            "If that permanent's mana value was 3 or less, return it to the battlefield tapped under your control."
                + " Exile it at the beginning of the next end step."
        ));
    }

    private VindictiveTriumph(final VindictiveTriumph card) {
        super(card);
    }

    @Override
    public VindictiveTriumph copy() {
        return new VindictiveTriumph(this);
    }
}

class VindictiveTriumphEffect extends OneShotEffect {

    public VindictiveTriumphEffect() {
        super(Outcome.Benefit);
        this.staticText = "return it to the battlefield tapped under your control. Exile it at the beginning of the next end step";
    }

    private VindictiveTriumphEffect(final VindictiveTriumphEffect effect) {
        super(effect);
        this.staticText = effect.staticText;
    }

    @Override
    public VindictiveTriumphEffect copy() {
        return new VindictiveTriumphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> targetIds = getTargetPointer().getTargets(game, source);

        ReturnToBattlefieldUnderYourControlTargetEffect returnEffect =
            new ReturnToBattlefieldUnderYourControlTargetEffect(true, true);
        returnEffect.setTargetPointer(getTargetPointer().copy());
        returnEffect.apply(game, source);

        for (UUID targetId : targetIds) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }

            ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
            exileEffect.setTargetPointer(new FixedTarget(permanent, game));
            game.addDelayedTriggeredAbility(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect),
                source
            );
        }
        return true;
    }
}
