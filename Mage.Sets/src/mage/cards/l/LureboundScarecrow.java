
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LureboundScarecrow extends CardImpl {

    public LureboundScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SCARECROW);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As Lurebound Scarecrow enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Detriment)));

        // When you control no permanents of the chosen color, sacrifice Lurebound Scarecrow.
        this.addAbility(new LureboundScarecrowTriggeredAbility());
    }

    private LureboundScarecrow(final LureboundScarecrow card) {
        super(card);
    }

    @Override
    public LureboundScarecrow copy() {
        return new LureboundScarecrow(this);
    }
}

class LureboundScarecrowTriggeredAbility extends StateTriggeredAbility {

    private static final String staticText = "When you control no permanents of the chosen color, sacrifice {this}.";

    public LureboundScarecrowTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    private LureboundScarecrowTriggeredAbility(final LureboundScarecrowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(getSourceId() + "_color");
            if (color != null) {
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(controllerId)) {
                    if (perm.getColor(game).contains(color)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public LureboundScarecrowTriggeredAbility copy() {
        return new LureboundScarecrowTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}
