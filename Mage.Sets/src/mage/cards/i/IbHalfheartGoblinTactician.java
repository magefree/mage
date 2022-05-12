
package mage.cards.i;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class IbHalfheartGoblinTactician extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountains");
    private static final FilterCreaturePermanent filterGoblin = new FilterCreaturePermanent("another Goblin you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
        filterGoblin.add(SubType.GOBLIN.getPredicate());
        filterGoblin.add(AnotherPredicate.instance);
        filterGoblin.add(TargetController.YOU.getControllerPredicate());
    }

    public IbHalfheartGoblinTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever another Goblin you control becomes blocked, sacrifice it. If you do, it deals 4 damage to each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(new IbHalfheartGoblinTacticianEffect(), false, filterGoblin, true));

        // Sacrifice two Mountains: Create two 1/1 red Goblin creature tokens.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new GoblinToken(), 2),
                new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true))));

    }

    private IbHalfheartGoblinTactician(final IbHalfheartGoblinTactician card) {
        super(card);
    }

    @Override
    public IbHalfheartGoblinTactician copy() {
        return new IbHalfheartGoblinTactician(this);
    }
}

class IbHalfheartGoblinTacticianEffect extends OneShotEffect {

    public IbHalfheartGoblinTacticianEffect() {
        super(Outcome.Damage);
        this.staticText = "sacrifice it. If you do, it deals 4 damage to each creature blocking it";
    }

    public IbHalfheartGoblinTacticianEffect(final IbHalfheartGoblinTacticianEffect effect) {
        super(effect);
    }

    @Override
    public IbHalfheartGoblinTacticianEffect copy() {
        return new IbHalfheartGoblinTacticianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent blockedCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (blockedCreature == null) {
            // it can't be sacrificed, nothing happens
            return true;
        }
        Set<UUID> blockingCreatures = new HashSet<>();
        for (CombatGroup combatGroup : game.getCombat().getGroups()) {
            if (combatGroup.getAttackers().contains(blockedCreature.getId())) {
                blockingCreatures.addAll(combatGroup.getBlockers());
            }
        }
        if (blockedCreature.sacrifice(source, game)) {
            for (UUID blockerId : blockingCreatures) {
                Permanent blockingCreature = game.getPermanent(blockerId);
                if (blockingCreature != null) {
                    blockingCreature.damage(4, blockedCreature.getId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
