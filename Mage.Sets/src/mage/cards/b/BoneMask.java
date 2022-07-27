package mage.cards.b;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetSource;

/**
 *
 * @author awjackson
 */
public final class BoneMask extends CardImpl {

    public BoneMask(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {tap}: The next time a source of your choice would deal damage to you this turn, prevent that damage. Exile cards from the top of your library equal to the damage prevented this way.
        Ability ability = new SimpleActivatedAbility(new BoneMaskEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BoneMask(final BoneMask card) {
        super(card);
    }

    @Override
    public BoneMask copy() {
        return new BoneMask(this);
    }
}

class BoneMaskEffect extends PreventionEffectImpl {

    private final TargetSource target;

    public BoneMaskEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "The next time a source of your choice would deal damage to you this turn, prevent that damage. "
                + "Exile cards from the top of your library equal to the damage prevented this way.";
        this.target = new TargetSource();
    }

    public BoneMaskEffect(final BoneMaskEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public BoneMaskEffect copy() {
        return new BoneMaskEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        this.used = true;
        this.discard();
        if (preventionData.getPreventedDamage() > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                Set<Card> cards = controller.getLibrary().getTopCards(game, preventionData.getPreventedDamage());
                controller.moveCards(cards, Zone.EXILED, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return (!this.used
                && super.applies(event, source, game)
                && event.getTargetId().equals(source.getControllerId())
                && event.getSourceId().equals(target.getFirstTarget())
        );
    }
}
