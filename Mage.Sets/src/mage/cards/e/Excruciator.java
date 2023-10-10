
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class Excruciator extends CardImpl {

    public Excruciator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Damage that would be dealt by Excruciator can't be prevented.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ExcruciatorEffect()));
    }

    private Excruciator(final Excruciator card) {
        super(card);
    }

    @Override
    public Excruciator copy() {
        return new Excruciator(this);
    }
}

class ExcruciatorEffect extends ContinuousRuleModifyingEffectImpl {

    public ExcruciatorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Damage that would be dealt by {this} can't be prevented";
    }

    private ExcruciatorEffect(final ExcruciatorEffect effect) {
        super(effect);
    }

    @Override
    public ExcruciatorEffect copy() {
        return new ExcruciatorEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

}
