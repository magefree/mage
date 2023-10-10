
package mage.cards.v;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author spjspj
 */
public final class VanquishersBanner extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public VanquishersBanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // As Vanquisher's Banner enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // Creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // Whenever you cast a creature spell of the chosen type, draw a card.
        this.addAbility(new DrawCardIfCreatureTypeAbility());

    }

    private VanquishersBanner(final VanquishersBanner card) {
        super(card);
    }

    @Override
    public VanquishersBanner copy() {
        return new VanquishersBanner(this);
    }
}

class DrawCardIfCreatureTypeAbility extends TriggeredAbilityImpl {

    public DrawCardIfCreatureTypeAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private DrawCardIfCreatureTypeAbility(final DrawCardIfCreatureTypeAbility ability) {
        super(ability);
    }

    @Override
    public DrawCardIfCreatureTypeAbility copy() {
        return new DrawCardIfCreatureTypeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(getSourceId(), game);
        if (subType != null) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && spell.isCreature(game)
                    && spell.hasSubtype(subType, game)
                    && spell.isControlledBy(getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a creature spell of the chosen type, draw a card.";
    }
}
