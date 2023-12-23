
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetPlayer;

/**
 *
 * @author brikr
 */
public final class HarshJudgment extends CardImpl {

    public HarshJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        

        // As Harsh Judgment enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // If an instant or sorcery spell of the chosen color would deal damage to you, it deals that damage to its controller instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HarshJudgmentEffect()));
    }

    private HarshJudgment(final HarshJudgment card) {
        super(card);
    }

    @Override
    public HarshJudgment copy() {
        return new HarshJudgment(this);
    }
}

class HarshJudgmentEffect extends RedirectionEffect {

    private static final FilterInstantOrSorceryCard instantOrSorceryFilter = new FilterInstantOrSorceryCard();

    public HarshJudgmentEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "If an instant or sorcery spell of the chosen color would deal damage to you, it deals that damage to its controller instead";
    }

    private HarshJudgmentEffect(final HarshJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(event.getTargetId().equals(source.getControllerId())) {
            Spell spell = null;
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject == null) {
                stackObject = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
            }
            if (stackObject instanceof Spell) {
                spell = (Spell) stackObject;
            }
            //Checks if damage is from a sorcery or instants and spell is of chosen color
            Permanent permanent = game.getPermanent(source.getSourceId());
            ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
            if (spell != null && instantOrSorceryFilter.match(spell.getCard(), game) && spell.getColor(game).contains(color)) {
                TargetPlayer target = new TargetPlayer();
                target.add(spell.getControllerId(), game);
                redirectTarget = target;
                return true;
            }
        }
        return false;
    }

    @Override
    public HarshJudgmentEffect copy() {
        return new HarshJudgmentEffect(this);
    }
}
