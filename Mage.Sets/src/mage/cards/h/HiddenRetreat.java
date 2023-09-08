package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PutCardFromHandOnTopOfLibraryCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author bunchOfDevs
 */
public class HiddenRetreat extends CardImpl {

    public HiddenRetreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        //Put a card from your hand on top of your library: Prevent all damage that would be dealt by target instant or sorcery spell this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HiddenRetreatEffect(), new PutCardFromHandOnTopOfLibraryCost());
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.addAbility(ability);

    }

    private HiddenRetreat(final HiddenRetreat hiddenRetreat) {
        super(hiddenRetreat);
    }

    @Override
    public HiddenRetreat copy() {
        return new HiddenRetreat(this);
    }
}

class HiddenRetreatEffect extends PreventionEffectImpl {

    public HiddenRetreatEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.staticText = "Prevent all damage that would be dealt by target instant or sorcery spell this turn.";
    }

    private HiddenRetreatEffect(final HiddenRetreatEffect effect) {
        super(effect);
    }

    @Override
    public HiddenRetreatEffect copy() {
        return new HiddenRetreatEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return (super.applies(event, source, game)
                && event instanceof DamageEvent
                && event.getAmount() > 0
                && game.getObject(source.getFirstTarget()) != null
                && game.getObject(event.getSourceId()) != null
                && game.getObject(source.getFirstTarget()).equals(game.getObject(event.getSourceId()))
                && game.getObject(source.getFirstTarget()).isInstantOrSorcery(game));
    }
}
