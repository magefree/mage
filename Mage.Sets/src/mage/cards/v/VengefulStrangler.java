package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.DoubleFacedCardHalf;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.CanBeSacrificedPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulStrangler extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VengefulStrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE}, "{1}{B}",
                "Strangling Grasp",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "B"
        );

        // Vengeful Strangler
        this.getLeftHalfCard().setPT(2, 1);

        // Vengeful Strangler can't block.
        this.getLeftHalfCard().addAbility(new CantBlockAbility());

        // When Vengeful Strangler dies, return it to the battlefield transformed under your control attached to target creature or planeswalker an opponent controls.
        Ability ability = new DiesSourceTriggeredAbility(new VengefulStranglerEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Strangling Grasp
        // Enchant creature or planeswalker an opponent controls
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // At the beginning of your upkeep, enchanted permanent's controller sacrifices a nonland permanent and loses 1 life.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new StranglingGraspEffect()));
    }

    private VengefulStrangler(final VengefulStrangler card) {
        super(card);
    }

    @Override
    public VengefulStrangler copy() {
        return new VengefulStrangler(this);
    }
}

class VengefulStranglerEffect extends OneShotEffect {

    VengefulStranglerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control " +
                "attached to target creature or planeswalker an opponent controls";
    }

    private VengefulStranglerEffect(final VengefulStranglerEffect effect) {
        super(effect);
    }

    @Override
    public VengefulStranglerEffect copy() {
        return new VengefulStranglerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null
                || game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }

        DoubleFacedCardHalf card = (DoubleFacedCardHalf) game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        game.getState().setValue("attachTo:" + card.getOtherSide().getId(), permanent);
        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            permanent.addAttachment(card.getOtherSide().getId(), source, game);
        }
        return true;
    }
}

class StranglingGraspEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CanBeSacrificedPredicate.instance);
    }

    StranglingGraspEffect() {
        super(Outcome.Benefit);
        staticText = "enchanted permanent's controller sacrifices a nonland permanent of their choice, then that player loses 1 life";
    }

    private StranglingGraspEffect(final StranglingGraspEffect effect) {
        super(effect);
    }

    @Override
    public StranglingGraspEffect copy() {
        return new StranglingGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanentOrLKIBattlefield)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .orElse(null);
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        if (target.canChoose(player.getId(), source, game)) {
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.sacrifice(source, game);
            }
        }
        player.loseLife(1, game, source, false);
        return true;
    }
}
