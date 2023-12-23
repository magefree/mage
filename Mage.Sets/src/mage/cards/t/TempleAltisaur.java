
package mage.cards.t;

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
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class TempleAltisaur extends CardImpl {

    public TempleAltisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // If a source would deal damage to another Dinosaur you control, prevent all but 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TempleAltisaurPreventEffect()));
    }

    private TempleAltisaur(final TempleAltisaur card) {
        super(card);
    }

    @Override
    public TempleAltisaur copy() {
        return new TempleAltisaur(this);
    }
}

class TempleAltisaurPreventEffect extends PreventionEffectImpl {

    public TempleAltisaurPreventEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "If a source would deal damage to another Dinosaur you control, prevent all but 1 of that damage";
        consumable = false;
    }

    private TempleAltisaurPreventEffect(final TempleAltisaurPreventEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        if (damage > 1) {
            amountToPrevent = damage - 1;
            preventDamageAction(event, source, game);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null
                    && !permanent.getId().equals(source.getSourceId())
                    && permanent.hasSubtype(SubType.DINOSAUR, game)
                    && permanent.isControlledBy(source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public TempleAltisaurPreventEffect copy() {
        return new TempleAltisaurPreventEffect(this);
    }
}
