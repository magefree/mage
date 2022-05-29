
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class DurkwoodTracker extends CardImpl {

    public DurkwoodTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {1}{G}, {tap}: If Durkwood Tracker is on the battlefield, it deals damage equal to its power to target attacking creature. That creature deals damage equal to its power to Durkwood Tracker.
        Ability ability = new SimpleActivatedAbility(new DurkwoodTrackerEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private DurkwoodTracker(final DurkwoodTracker card) {
        super(card);
    }

    @Override
    public DurkwoodTracker copy() {
        return new DurkwoodTracker(this);
    }
}

class DurkwoodTrackerEffect extends OneShotEffect {

    DurkwoodTrackerEffect() {
        super(Outcome.Benefit);
        this.staticText = "if {this} is on the battlefield, "
                + "it deals damage equal to its power to target attacking creature. "
                + "That creature deals damage equal to its power to {this}";
    }

    DurkwoodTrackerEffect(final DurkwoodTrackerEffect effect) {
        super(effect);
    }

    @Override
    public DurkwoodTrackerEffect copy() {
        return new DurkwoodTrackerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null
                || permanent.getZoneChangeCounter(game)
                != source.getSourceObjectZoneChangeCounter()) {
            return false;
        }
        Permanent targeted = game.getPermanent(source.getFirstTarget());
        if (targeted == null) {
            return false;
        }
        targeted.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true);
        permanent.damage(targeted.getPower().getValue(), targeted.getId(), source, game, false, true);
        return true;
    }
}
