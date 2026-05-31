package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Mercenaries extends CardImpl {

    public Mercenaries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {3}: The next time Mercenaries would deal damage to you this turn, prevent that damage. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new MercenariesPreventDamageEffect(), new GenericManaCost(3));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);

    }

    private Mercenaries(final Mercenaries card) {
        super(card);
    }

    @Override
    public Mercenaries copy() {
        return new Mercenaries(this);
    }
}

class MercenariesPreventDamageEffect extends PreventionEffectImpl {

    MercenariesPreventDamageEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "The next time {this} would deal damage to you this turn, prevent that damage";
    }

    private MercenariesPreventDamageEffect(final MercenariesPreventDamageEffect effect) {
        super(effect);
    }

    @Override
    public MercenariesPreventDamageEffect copy() {
        return new MercenariesPreventDamageEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        super.replaceEvent(event, source, game);
        discard(); // single use
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            return event.getSourceId().equals(source.getSourceId()) // source permanent dealing damage
                    && event.getTargetId().equals(source.getControllerId()); // to you who activated ability
        }
        return false;
    }

}
