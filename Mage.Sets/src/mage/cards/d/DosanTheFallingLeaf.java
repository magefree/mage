
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Loki
 */
public final class DosanTheFallingLeaf extends CardImpl {

    public DosanTheFallingLeaf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Players can cast spells only during their own turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DosanTheFallingLeafEffect()));
    }

    private DosanTheFallingLeaf(final DosanTheFallingLeaf card) {
        super(card);
    }

    @Override
    public DosanTheFallingLeaf copy() {
        return new DosanTheFallingLeaf(this);
    }
}

class DosanTheFallingLeafEffect extends ContinuousRuleModifyingEffectImpl {

    DosanTheFallingLeafEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can cast spells only during their own turns";
    }

    DosanTheFallingLeafEffect(final DosanTheFallingLeafEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL && !game.isActivePlayer(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DosanTheFallingLeafEffect copy() {
        return new DosanTheFallingLeafEffect(this);
    }
}
