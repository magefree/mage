
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class GoblinBrawler extends CardImpl {

    public GoblinBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Goblin Brawler can't be equipped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeEquippedSourceEffect()));
    }

    private GoblinBrawler(final GoblinBrawler card) {
        super(card);
    }

    @Override
    public GoblinBrawler copy() {
        return new GoblinBrawler(this);
    }
}

class CantBeEquippedSourceEffect extends ContinuousRuleModifyingEffectImpl {

    private CantBeEquippedSourceEffect(final CantBeEquippedSourceEffect effect) {
        super(effect);
    }

    public CantBeEquippedSourceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "{this} can't be equipped";
    }

    @Override
    public CantBeEquippedSourceEffect copy() {
        return new CantBeEquippedSourceEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACH;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.hasSubtype(SubType.EQUIPMENT, game)) {
                return true;
            }
        }
        return false;
    }
}
