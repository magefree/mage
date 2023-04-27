package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BeastToken;
import mage.game.stack.Spell;
import mage.target.Target;

/**
 *
 * @author TheElk801
 */
public final class DruidOfHorns extends CardImpl {

    public DruidOfHorns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast an Aura spell that targets Druid of Horns, create a 3/3 green Beast creature token.
        this.addAbility(new DruidOfHornsTriggeredAbility());
    }

    private DruidOfHorns(final DruidOfHorns card) {
        super(card);
    }

    @Override
    public DruidOfHorns copy() {
        return new DruidOfHorns(this);
    }
}

class DruidOfHornsTriggeredAbility extends TriggeredAbilityImpl {

    public DruidOfHornsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new BeastToken()), false);
        setTriggerPhrase("Whenever you cast an Aura spell that targets {this}, ");
    }

    public DruidOfHornsTriggeredAbility(final DruidOfHornsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DruidOfHornsTriggeredAbility copy() {
        return new DruidOfHornsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (checkSpell(spell, game)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSpell(Spell spell, Game game) {
        if (spell != null && spell.hasSubtype(SubType.AURA, game)) {
            SpellAbility sa = spell.getSpellAbility();
            for (UUID modeId : sa.getModes().getSelectedModes()) {
                Mode mode = sa.getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    if (!target.isNotTarget() && target.getTargets().contains(this.getSourceId())) {
                        return true;
                    }
                }
                for (Effect effect : mode.getEffects()) {
                    for (UUID targetId : effect.getTargetPointer().getTargets(game, sa)) {
                        if (targetId.equals(this.getSourceId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
