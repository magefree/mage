package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.GoblinToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class GoblinGoliath extends CardImpl {

    public GoblinGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Goblin Goliath enters the battlefield, create a number of 1/1 red Goblin creature tokens equal to the number of opponents you have.
        Effect effect = new CreateTokenEffect(new GoblinToken(), OpponentsCount.instance);
        effect.setText("create a number of 1/1 red Goblin creature tokens equal to the number of opponents you have");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));

        // {3}{R}, {T}: If a source you control would deal damage to an opponent this turn, it deals double that damage to that player instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinGoliathDamageEffect(), new ManaCostsImpl<>("{3}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private GoblinGoliath(final GoblinGoliath card) {
        super(card);
    }

    @Override
    public GoblinGoliath copy() {
        return new GoblinGoliath(this);
    }
}

class GoblinGoliathDamageEffect extends ReplacementEffectImpl {

    public GoblinGoliathDamageEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "If a source you control would deal damage to an opponent this turn, it deals double that damage to that player instead.";
    }

    public GoblinGoliathDamageEffect(final GoblinGoliathDamageEffect effect) {
        super(effect);
    }

    @Override
    public GoblinGoliathDamageEffect copy() {
        return new GoblinGoliathDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID sourceControllerID = source.getControllerId();
        UUID damageControllerID = game.getControllerId(event.getSourceId());
        UUID damageTargetID = event.getTargetId();
        if (sourceControllerID != null && damageControllerID != null && damageTargetID != null) {
            // our damage
            if (damageControllerID.equals(sourceControllerID)) {
                // to opponent only
                if (game.getOpponents(sourceControllerID).contains(damageTargetID)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
