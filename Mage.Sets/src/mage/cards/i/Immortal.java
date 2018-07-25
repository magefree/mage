package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author NinthWorld
 */
public final class Immortal extends CardImpl {

    public Immortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If a source would deal 3 or more damage to Immortal, it deals 2 damage instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ImmortalEffect()));
    }

    public Immortal(final Immortal card) {
        super(card);
    }

    @Override
    public Immortal copy() {
        return new Immortal(this);
    }
}

class ImmortalEffect extends PreventionEffectImpl {

    public ImmortalEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If a source would deal 3 or more damage to {this}, it deals 2 damage instead";
    }

    public ImmortalEffect(final ImmortalEffect effect) {
        super(effect);
    }

    @Override
    public ImmortalEffect copy() {
        return new ImmortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if(event.getAmount() >= 3) {
            event.setAmount(2);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId());
    }

}