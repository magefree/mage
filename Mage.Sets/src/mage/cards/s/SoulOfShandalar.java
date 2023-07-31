
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 * @author noxx
 */
public final class SoulOfShandalar extends CardImpl {

    public SoulOfShandalar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {3}{R}{R}: Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoulOfShandalarEffect(), new ManaCostsImpl<>("{3}{R}{R}"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        ability.addTarget(new SoulOfShandalarTarget());
        this.addAbility(ability);

        // {3}{R}{R}, Exile Soul of Shandalar from your graveyard: Soul of Shandalar deals 3 damage to target player and 3 damage to up to one target creature that player controls.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new SoulOfShandalarEffect(), new ManaCostsImpl<>("{3}{R}{R}"));
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        ability.addTarget(new SoulOfShandalarTarget());
        this.addAbility(ability);
    }

    private SoulOfShandalar(final SoulOfShandalar card) {
        super(card);
    }

    @Override
    public SoulOfShandalar copy() {
        return new SoulOfShandalar(this);
    }
}

class SoulOfShandalarEffect extends OneShotEffect {

    public SoulOfShandalarEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to target player or planeswalker "
                + "and 3 damage to up to one target creature that player or that planeswalker's controller controls";
    }

    public SoulOfShandalarEffect(final SoulOfShandalarEffect effect) {
        super(effect);
    }

    @Override
    public SoulOfShandalarEffect copy() {
        return new SoulOfShandalarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.damagePlayerOrPermanent(source.getTargets().get(0).getFirstTarget(), 3, source.getSourceId(), source, game, false, true);
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null) {
            creature.damage(3, source.getSourceId(), source, game, false, true);
        }
        return true;
    }
}

class SoulOfShandalarTarget extends TargetPermanent {

    public SoulOfShandalarTarget() {
        super(0, 1, new FilterCreaturePermanent("creature that the targeted player or planeswalker's controller controls"), false);
    }

    public SoulOfShandalarTarget(final SoulOfShandalarTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        UUID firstTarget = player.getId();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.isControlledBy(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject object = game.getObject(source);

        for (StackObject item : game.getState().getStack()) {
            if (item.getId().equals(source.getSourceId())) {
                object = item;
            }
            if (item.getSourceId().equals(source.getSourceId())) {
                object = item;
            }
        }

        if (object instanceof StackObject) {
            UUID playerId = ((StackObject) object).getStackAbility().getFirstTarget();
            Player player = game.getPlayerOrPlaneswalkerController(playerId);
            if (player != null) {
                for (UUID targetId : availablePossibleTargets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.isControlledBy(player.getId())) {
                        possibleTargets.add(targetId);
                    }
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public SoulOfShandalarTarget copy() {
        return new SoulOfShandalarTarget(this);
    }
}
