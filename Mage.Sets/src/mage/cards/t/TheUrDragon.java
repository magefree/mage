package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public final class TheUrDragon extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dragon spells");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public TheUrDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // <i>Eminence</i> &mdash; As long as The Ur-Dragon is in the command zone or on the battlefield, other Dragon spells you cast cost {1} less to cast.
        Effect effect = new SpellsCostReductionControllerEffect(filter, 1);
        effect.setText("As long as {this} is in the command zone or on the battlefield, other Dragon spells you cast cost {1} less to cast");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);
        effect = new SpellsCostReductionControllerEffect(filter, 1);
        effect.setText("");
        ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Dragons you control attack, draw that many cards, then you may put a permanent card from your hand onto the battlefield
        this.addAbility(new TheUrDragonTriggeredAbility());
    }

    private TheUrDragon(final TheUrDragon card) {
        super(card);
    }

    @Override
    public TheUrDragon copy() {
        return new TheUrDragon(this);
    }
}

class TheUrDragonTriggeredAbility extends TriggeredAbilityImpl {

    public TheUrDragonTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public TheUrDragonTriggeredAbility(final TheUrDragonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheUrDragonTriggeredAbility copy() {
        return new TheUrDragonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int attackingDragons = 0;
        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(attacker);
            if (creature != null
                    && creature.getControllerId() != null
                    && creature.isControlledBy(this.getControllerId())
                    && creature.hasSubtype(SubType.DRAGON, game)) {
                attackingDragons++;
            }
        }
        if (attackingDragons > 0) {
            this.getEffects().clear();
            addEffect(new DrawCardSourceControllerEffect(attackingDragons));
            addEffect(new PutCardFromHandOntoBattlefieldEffect());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Dragons you control attack, draw that many cards, then you may put a permanent card from your hand onto the battlefield.";
    }
}
