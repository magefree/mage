package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class PrimordialMist extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("face down permanent");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public PrimordialMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // At the beginning of your end step, you may manifest the top card of your library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ManifestEffect(1), TargetController.YOU, true));

        // Exile a face-down permanent you control face-up: You may play that card this turn
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PrimordialMistCastFromExileEffect(),
                new PrimordialMistCost(target));
        this.addAbility(ability);
    }

    private PrimordialMist(final PrimordialMist card) {
        super(card);
    }

    @Override
    public PrimordialMist copy() {
        return new PrimordialMist(this);
    }
}

class PrimordialMistCost extends CostImpl {

    TargetPermanent target;

    public PrimordialMistCost(TargetPermanent target) {
        this.target = target;
        this.text = "Exile a face-down permanent you control face-up";
    }

    public PrimordialMistCost(final PrimordialMistCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public PrimordialMistCost copy() {
        return new PrimordialMistCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(source.getSourceId(), controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            if (target.choose(Outcome.Exile, controllerId, source.getSourceId(), game)) {
                Card card = game.getCard(source.getSourceId());
                if (card != null) {
                    Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                    if (sourcePermanent != null) {
                        Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
                        Card targetCard = game.getCard(target.getFirstTarget());
                        if (targetPermanent != null
                                && targetCard != null) {
                            String exileName = sourcePermanent.getIdName() + " <this card may be played the turn it was exiled>";
                            controller.moveCardsToExile(targetPermanent, source, game, true, source.getSourceId(), exileName);
                            targetPermanent.setFaceDown(false, game);
                            PrimordialMistCastFromExileEffect effect = new PrimordialMistCastFromExileEffect();
                            effect.setTargetPointer(new FixedTarget(targetCard.getId()));
                            game.addEffect(effect, ability);
                            this.setPaid();
                        }
                    }
                }
                this.setPaid();
                return true;
            }
        }
        return false;
    }
}

class PrimordialMistCastFromExileEffect extends AsThoughEffectImpl {

    public PrimordialMistCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Exile a face-down permanent you control face up: You may play that card this turn.";
    }

    public PrimordialMistCastFromExileEffect(final PrimordialMistCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PrimordialMistCastFromExileEffect copy() {
        return new PrimordialMistCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && (game.getCard(targetPointer.getFirst(game, source)) != null)
                && objectId == targetPointer.getFirst(game, source);
    }
}
