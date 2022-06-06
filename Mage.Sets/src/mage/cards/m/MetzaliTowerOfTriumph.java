
package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.RandomUtil;
import mage.watchers.Watcher;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class MetzaliTowerOfTriumph extends CardImpl {

    public MetzaliTowerOfTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.nightCard = true;

        // <i>(Transforms from Path of Mettle.)</i>
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("<i>(Transforms from Path of Mettle.)</i>"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // {t}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {1}{R}, {T}: Metzali, Tower of Triumph deals 2 damage to each opponent.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(2, TargetController.OPPONENT), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {2}{W}, {T}: Choose a creature at random that attacked this turn. Destroy that creature.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MetzaliTowerOfTriumphEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addWatcher(new AttackedThisTurnWatcher());
        this.addAbility(ability);

    }

    private MetzaliTowerOfTriumph(final MetzaliTowerOfTriumph card) {
        super(card);
    }

    @Override
    public MetzaliTowerOfTriumph copy() {
        return new MetzaliTowerOfTriumph(this);
    }

}

class MetzaliTowerOfTriumphEffect extends OneShotEffect {

    public MetzaliTowerOfTriumphEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose a creature at random that attacked this turn. Destroy that creature";
    }

    public MetzaliTowerOfTriumphEffect(final MetzaliTowerOfTriumphEffect effect) {
        super(effect);
    }

    @Override
    public MetzaliTowerOfTriumphEffect copy() {
        return new MetzaliTowerOfTriumphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher instanceof AttackedThisTurnWatcher) {
            Set<MageObjectReference> attackedThisTurn = ((AttackedThisTurnWatcher) watcher).getAttackedThisTurnCreatures();
            List<Permanent> available = new ArrayList<>();
            for (MageObjectReference mor : attackedThisTurn) {
                Permanent permanent = mor.getPermanent(game);
                if (permanent != null && permanent.isCreature(game)) {
                    available.add(permanent);
                }
            }
            if (!available.isEmpty()) {
                Permanent permanent = available.get(RandomUtil.nextInt(available.size()));
                if (permanent != null) {
                    permanent.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }
}
