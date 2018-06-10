
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Pandemonium extends CardImpl {

    private final UUID originalId;

    public Pandemonium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever a creature enters the battlefield, that creature's controller may have it deal damage equal to its power to any target of their choice.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new PandemoniumEffect(), StaticFilters.FILTER_PERMANENT_CREATURE, false, SetTargetPointer.PERMANENT, "");
        ability.addTarget(new TargetAnyTarget());
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    public Pandemonium(final Pandemonium card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            UUID creatureId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null) {
                ability.getTargets().get(0).setTargetController(creature.getControllerId());
            }
        }
    }

    @Override
    public Pandemonium copy() {
        return new Pandemonium(this);
    }
}

class PandemoniumEffect extends OneShotEffect {

    public PandemoniumEffect() {
        super(Outcome.Benefit);
        this.staticText = "that creature's controller may have it deal damage equal to its power to any target of their choice";
    }

    public PandemoniumEffect(final PandemoniumEffect effect) {
        super(effect);
    }

    @Override
    public PandemoniumEffect copy() {
        return new PandemoniumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent enteringCreature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
            if (enteringCreature != null) {
                Permanent targetPermanent = game.getPermanent(source.getTargets().getFirstTarget());
                if (targetPermanent != null) {
                    targetPermanent.damage(enteringCreature.getPower().getValue(), enteringCreature.getId(), game, false, true);
                } else {
                    Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
                    if (targetPlayer != null) {
                        targetPlayer.damage(enteringCreature.getPower().getValue(), enteringCreature.getId(), game, false, true);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
