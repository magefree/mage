
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.MorphAbility;
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
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class FortuneThief extends CardImpl {

    public FortuneThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Damage that would reduce your life total to less than 1 reduces it to 1 instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FortuneThiefReplacementEffect()));
        // Morph {R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{R}{R}")));
    }

    private FortuneThief(final FortuneThief card) {
        super(card);
    }

    @Override
    public FortuneThief copy() {
        return new FortuneThief(this);
    }
}

class FortuneThiefReplacementEffect extends ReplacementEffectImpl {

    public FortuneThiefReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    public FortuneThiefReplacementEffect(final FortuneThiefReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FortuneThiefReplacementEffect copy() {
        return new FortuneThiefReplacementEffect(this);
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
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

}