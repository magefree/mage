
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class AnZerrinRuins extends CardImpl {

    public AnZerrinRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // As An-Zerrin Ruins enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.UnboostCreature)));

        // Creatures of the chosen type don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnZerrinRuinsDontUntapEffect()));
    }

    private AnZerrinRuins(final AnZerrinRuins card) {
        super(card);
    }

    @Override
    public AnZerrinRuins copy() {
        return new AnZerrinRuins(this);
    }
}

class AnZerrinRuinsDontUntapEffect extends DontUntapInControllersUntapStepAllEffect {

    public AnZerrinRuinsDontUntapEffect() {
        super(Duration.WhileOnBattlefield, TargetController.ANY, new FilterCreaturePermanent());
        this.staticText = "Creatures of the chosen type don't untap during their controllers' untap steps";
    }

    public AnZerrinRuinsDontUntapEffect(final AnZerrinRuinsDontUntapEffect effect) {
        super(effect);
    }

    @Override
    public AnZerrinRuinsDontUntapEffect copy() {
        return new AnZerrinRuinsDontUntapEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (permanent.hasSubtype(ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game), game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
