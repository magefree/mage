
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author KholdFuzion
 */
public final class SustainingSpirit extends CardImpl {

    public SustainingSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Cumulative upkeep {1}{W}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}{W}")));
        // Damage that would reduce your life total to less than 1 reduces it to 1 instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SustainingSpiritReplacementEffect()));

    }

    private SustainingSpirit(final SustainingSpirit card) {
        super(card);
    }

    @Override
    public SustainingSpirit copy() {
        return new SustainingSpirit(this);
    }
}

class SustainingSpiritReplacementEffect extends ReplacementEffectImpl {

    public SustainingSpiritReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    public SustainingSpiritReplacementEffect(final SustainingSpiritReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SustainingSpiritReplacementEffect copy() {
        return new SustainingSpiritReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS;
    }    
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && (controller.getLife() > 0) &&(controller.getLife() - event.getAmount()) < 1
                    && event.getPlayerId().equals(controller.getId())
                    ) {
                event.setAmount(controller.getLife() - 1);
                //unsure how to make this comply with
                // 10/1/2008: The ability doesn't change how much damage is dealt;
                // it just changes how much life that damage makes you lose.
                // An effect such as Spirit Link will see the full amount of damage being dealt.
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

}