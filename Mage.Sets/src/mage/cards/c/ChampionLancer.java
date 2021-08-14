
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author TheElk801
 */
public final class ChampionLancer extends CardImpl {

    public ChampionLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prevent all damage that would be dealt to Champion Lancer by creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToSourceByCardTypeEffect()));
    }

    private ChampionLancer(final ChampionLancer card) {
        super(card);
    }

    @Override
    public ChampionLancer copy() {
        return new ChampionLancer(this);
    }
}

class PreventDamageToSourceByCardTypeEffect extends PreventAllDamageToSourceEffect {

    public PreventDamageToSourceByCardTypeEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public PreventDamageToSourceByCardTypeEffect(final PreventDamageToSourceByCardTypeEffect effect) {
        super(effect.duration);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject != null && sourceObject.isCreature(game)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PreventAllDamageToSourceEffect copy() {
        return new PreventAllDamageToSourceEffect(this);
    }

}
