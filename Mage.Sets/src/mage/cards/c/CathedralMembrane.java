package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.BlockedAttackerWatcher;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class CathedralMembrane extends CardImpl {

    public CathedralMembrane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // <i>({W/P} can be paid with either {W} or 2 life.)</i>
        this.addAbility(DefenderAbility.getInstance());

        // When Cathedral Membrane dies during combat, it deals 6 damage to each creature it blocked this combat.
        this.addAbility(new CathedralMembraneAbility());
    }

    private CathedralMembrane(final CathedralMembrane card) {
        super(card);
    }

    @Override
    public CathedralMembrane copy() {
        return new CathedralMembrane(this);
    }
}

class CathedralMembraneAbility extends DiesSourceTriggeredAbility {

    CathedralMembraneAbility() {
        super(new CathedralMembraneEffect());
        setTriggerPhrase("When {this} dies during combat, ");
    }

    private CathedralMembraneAbility(CathedralMembraneAbility ability) {
        super(ability);
    }

    @Override
    public CathedralMembraneAbility copy() {
        return new CathedralMembraneAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getTurnPhaseType() == TurnPhase.COMBAT && super.checkTrigger(event, game);
    }

}

class CathedralMembraneEffect extends OneShotEffect {

    CathedralMembraneEffect() {
        super(Outcome.Damage);
        staticText = "it deals 6 damage to each creature it blocked this combat";
    }

    private CathedralMembraneEffect(final CathedralMembraneEffect effect) {
        super(effect);
    }

    @Override
    public CathedralMembraneEffect copy() {
        return new CathedralMembraneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        BlockedAttackerWatcher watcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
        if (watcher != null && permanent != null) {
            for (Permanent p : watcher.getBlockedCreatures(new MageObjectReference(permanent, game), game)) {
                p.damage(6, source.getSourceId(), source, game, false, true);
            }
        }
        return true;
    }
}
