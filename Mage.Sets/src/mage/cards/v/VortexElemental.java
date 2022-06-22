package mage.cards.v;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VortexElemental extends CardImpl {

    public VortexElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {U}: Put Vortex Elemental and each creature blocking or blocked by it on top of their owners' libraries, then those players shuffle their libraries.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new VortexElementalEffect(), new ManaCostsImpl<>("{U}")));

        // {3}{U}{U}: Target creature blocks Vortex Elemental this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(), new ManaCostsImpl<>("{3}{U}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private VortexElemental(final VortexElemental card) {
        super(card);
    }

    @Override
    public VortexElemental copy() {
        return new VortexElemental(this);
    }
}

class VortexElementalEffect extends OneShotEffect {

    public VortexElementalEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put {this} and each creature blocking or blocked by it on top of their owners' libraries, then those players shuffle";
    }

    public VortexElementalEffect(final VortexElementalEffect effect) {
        super(effect);
    }

    @Override
    public VortexElementalEffect copy() {
        return new VortexElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Combat combat = game.getState().getCombat();
            Set<UUID> creaturesToReturn = new HashSet<>();
            Set<UUID> playersToShuffle = new HashSet<>();
            creaturesToReturn.add(source.getSourceId());
            if (combat != null) {
                for (CombatGroup combatGroup : combat.getGroups()) {
                    if (combatGroup.getAttackers().contains(source.getSourceId())) {
                        creaturesToReturn.addAll(combatGroup.getBlockers());
                    } else if (combatGroup.getBlockers().contains(source.getSourceId())) {
                        creaturesToReturn.addAll(combatGroup.getAttackers());
                    }
                }
            }
            for (UUID creatureId : creaturesToReturn) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    playersToShuffle.add(creature.getControllerId());
                }
            }
            Cards toLib = new CardsImpl(creaturesToReturn);
            controller.putCardsOnTopOfLibrary(toLib, game, source, false);
            for (UUID playerId : playersToShuffle) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.shuffleLibrary(source, game);
                }
            }

            return true;
        }

        return false;
    }
}
