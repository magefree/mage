package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

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
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE}, "{1}{B}",
                "Strangling Grasp",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "B"
        );
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
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new StranglingGraspEffect(), TargetController.YOU, false
        ));
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

        Card card = game.getCard(source.getSourceId());
        if (card == null || card.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter() + 1) {
            return false;
        }

        TransformingDoubleFacedCard.setCardTransformed(card, game);
        game.getState().setValue("attachTo:" + source.getSourceId(), permanent);
        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            permanent.addAttachment(card.getId(), source, game);
        }
        return true;
    }
}

class StranglingGraspEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    StranglingGraspEffect() {
        super(Outcome.Benefit);
        staticText = "enchanted permanent's controller sacrifices a nonland permanent, then that player loses 1 life";
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
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return false;
        }
        Permanent attachedTo = game.getPermanentOrLKIBattlefield(sourcePermanent.getAttachedTo());
        if (attachedTo == null) {
            return false;
        }
        Player player = game.getPlayer(attachedTo.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
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
