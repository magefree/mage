
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PlayerCastCreatureWatcher;

/**
 *
 * @author LevelX2
 */
public final class GoblinCohort extends CardImpl {

    public GoblinCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Goblin Cohort can't attack unless you've cast a creature spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GoblinCohortEffect()), new PlayerCastCreatureWatcher());

    }

    public GoblinCohort(final GoblinCohort card) {
        super(card);
    }

    @Override
    public GoblinCohort copy() {
        return new GoblinCohort(this);
    }
}

class GoblinCohortEffect extends RestrictionEffect {

    public GoblinCohortEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you've cast a creature spell this turn";
    }

    public GoblinCohortEffect(final GoblinCohortEffect effect) {
        super(effect);
    }

    @Override
    public GoblinCohortEffect copy() {
        return new GoblinCohortEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            PlayerCastCreatureWatcher watcher = (PlayerCastCreatureWatcher) game.getState().getWatchers().get(PlayerCastCreatureWatcher.class.getSimpleName());
            if (watcher != null && !watcher.playerDidCastCreatureThisTurn(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}

