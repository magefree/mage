package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AfterCombatCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockedAttackerWatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author L_J
 */
public final class GlyphOfReincarnation extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall creature");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public GlyphOfReincarnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Cast this spell only after combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null, AfterCombatCondition.instance, "Cast this spell only after combat"));

        // Destroy all creatures that were blocked by target Wall this turn. They can’t be regenerated. For each creature that died this way, put a creature card from the graveyard of the player who controlled that creature the last time it became blocked by that Wall onto the battlefield under its owner’s control.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new GlyphOfReincarnationEffect());
        this.getSpellAbility().addWatcher(new BlockedAttackerWatcher());
    }

    private GlyphOfReincarnation(final GlyphOfReincarnation card) {
        super(card);
    }

    @Override
    public GlyphOfReincarnation copy() {
        return new GlyphOfReincarnation(this);
    }
}

class GlyphOfReincarnationEffect extends OneShotEffect {

    public GlyphOfReincarnationEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures that were blocked by target Wall this turn. They can't be regenerated. For each creature that died this way, put a creature card from the graveyard of the player who controlled that creature the last time it became blocked by that Wall onto the battlefield under its owner's control";
    }

    public GlyphOfReincarnationEffect(final GlyphOfReincarnationEffect effect) {
        super(effect);
    }

    @Override
    public GlyphOfReincarnationEffect copy() {
        return new GlyphOfReincarnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetWall = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && targetWall != null) {
            BlockedAttackerWatcher watcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
            if (watcher != null) {
                Map<UUID, Player> destroyed = new HashMap<>();
                for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                    if (!creature.getId().equals(targetWall.getId())) {
                        if (watcher.creatureHasBlockedAttacker(new MageObjectReference(creature, game), new MageObjectReference(targetWall, game), game)) {
                            if (creature.destroy(source, game, true)
                                    && game.getState().getZone(creature.getId()) == Zone.GRAVEYARD) { // If a commander is replaced to command zone, the creature does not die
                                Player permController = game.getPlayer(creature.getControllerId());
                                if (permController != null) {
                                    destroyed.put(creature.getId(), permController);
                                }
                            }
                        }
                    }
                }
                // For each creature that died this way, put a creature card from the graveyard of the player who controlled that creature the last time it became blocked by that Wall 
                // onto the battlefield under its owner’s control
                for (Map.Entry<UUID, Player> entry : destroyed.entrySet()) {
                    Permanent permanent = (Permanent) game.getLastKnownInformation(entry.getKey(), Zone.BATTLEFIELD);
                    Player player = entry.getValue();
                    if (permanent != null && player != null) {
                        FilterCreatureCard filter = new FilterCreatureCard("a creature card from " + player.getName() + "'s graveyard");
                        filter.add(new OwnerIdPredicate(player.getId()));
                        Target targetCreature = new TargetCardInGraveyard(filter);
                        targetCreature.setNotTarget(true);
                        if (targetCreature.canChoose(controller.getId(), source, game)
                                && controller.chooseTarget(outcome, targetCreature, source, game)) {
                            Card card = game.getCard(targetCreature.getFirstTarget());
                            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                                controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
                            }
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
