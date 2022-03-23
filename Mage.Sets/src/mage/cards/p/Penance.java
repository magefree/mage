
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCardFromHandOnTopOfLibraryCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

/**
 *
 * @author jeffwadsworth
 */
public final class Penance extends CardImpl {

    public Penance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Put a card from your hand on top of your library: The next time a black or red source of your choice would deal damage this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PenanceEffect(), new PutCardFromHandOnTopOfLibraryCost()));

    }

    private Penance(final Penance card) {
        super(card);
    }

    @Override
    public Penance copy() {
        return new Penance(this);
    }
}

class PenanceEffect extends PreventionEffectImpl {

    private final TargetSource target;

    public PenanceEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.staticText = "The next time a black or red source of your choice would deal damage to you this turn, prevent that damage.";
        this.target = new TargetSource();
    }

    public PenanceEffect(final PenanceEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public PenanceEffect copy() {
        return new PenanceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        this.used = true;
        this.discard(); // only one use        
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used
                && super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())
                    && event.getSourceId().equals(target.getFirstTarget())) {
                return (game.getObject(target.getFirstTarget()).getColor(game).contains(ObjectColor.BLACK)
                        || game.getObject(target.getFirstTarget()).getColor(game).contains(ObjectColor.RED));
            }
        }
        return false;
    }
}