
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.PermanentCard;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;

/**
 * @author noxx
 */
public final class SigardaHostOfHerons extends CardImpl {

    public SigardaHostOfHerons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HexproofAbility.getInstance());

        // Spells and abilities your opponents control can't cause you to sacrifice permanents.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SigardaHostOfHeronsEffect()));
    }

    public SigardaHostOfHerons(final SigardaHostOfHerons card) {
        super(card);
    }

    @Override
    public SigardaHostOfHerons copy() {
        return new SigardaHostOfHerons(this);
    }
}

class SigardaHostOfHeronsEffect extends ContinuousRuleModifyingEffectImpl {

    public SigardaHostOfHeronsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Spells and abilities your opponents control can't cause you to sacrifice permanents";
    }

    public SigardaHostOfHeronsEffect(final SigardaHostOfHeronsEffect effect) {
        super(effect);
    }

    @Override
    public SigardaHostOfHeronsEffect copy() {
        return new SigardaHostOfHeronsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object instanceof PermanentCard) {
                if (game.getOpponents(source.getControllerId()).contains(((PermanentCard) object).getControllerId())) {
                    return true;
                }
            }
            if (object instanceof Spell) {
                if (game.getOpponents(source.getControllerId()).contains(((Spell) object).getControllerId())) {
                    return true;
                }
            }
            if (object instanceof Card) {
                if (game.getOpponents(source.getControllerId()).contains(((Card) object).getOwnerId())) {
                    return true;
                }
            }
            if (object instanceof StackAbility) {
                if (game.getOpponents(source.getControllerId()).contains(((StackAbility) object).getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
