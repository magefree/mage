package mage.cards.h;

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

import java.util.UUID;

public class HiddenRetreat extends CardImpl {

    public HiddenRetreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        //Put a card from your hand on top of your library: Prevent all damage that would be dealt by target instant or sorcery spell this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new HiddenRetreatEffect(), new PutCardFromHandOnTopOfLibraryCost()));


    }

    public HiddenRetreat(final HiddenRetreat hiddenRetreat) {
        super(hiddenRetreat);
    }

    public HiddenRetreat copy() {
        return new HiddenRetreat(this);
    }
}

class HiddenRetreatEffect extends PreventionEffectImpl {

    private final TargetSource target;

    public HiddenRetreatEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.staticText = "Prevent all damage that would be dealt by target instant or sorcery spell this turn.";
        this.target = new TargetSource();
    }

    public HiddenRetreatEffect(final HiddenRetreatEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public HiddenRetreatEffect copy() {
        return new HiddenRetreatEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
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
                    && event.getSourceId().equals(target.getFirstTarget()))
                return game.getObject(target.getFirstTarget()).isInstantOrSorcery();
        }
        return false;
    }

}
