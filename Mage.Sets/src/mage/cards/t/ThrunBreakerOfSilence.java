package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author # -*- AhmadYProjects-*- , xenohedron
 */
public final class ThrunBreakerOfSilence extends CardImpl {



    public ThrunBreakerOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CantBeCounteredSourceEffect()));
        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Thrun, Breaker of Silence can't be the target of nongreen spells your opponents control or abilities from nongreen sources your opponents control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThrunBreakerOfSilenceEffect()));
        // As long as it's your turn, Thrun has indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                        MyTurnCondition.instance,
                        "As long as it's your turn, {this} has indestructible"))
                .addHint(MyTurnHint.instance));
    }

    private ThrunBreakerOfSilence(final ThrunBreakerOfSilence card) {
        super(card);
    }

    @Override
    public ThrunBreakerOfSilence copy() {
        return new ThrunBreakerOfSilence(this);
    }
}

class ThrunBreakerOfSilenceEffect extends ContinuousRuleModifyingEffectImpl {

    public ThrunBreakerOfSilenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "{this} can't be the target of nongreen spells your opponents control or abilities from nongreen sources your opponents control";
    }

    public ThrunBreakerOfSilenceEffect(final mage.cards.t.ThrunBreakerOfSilenceEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.t.ThrunBreakerOfSilenceEffect copy() {
        return new mage.cards.t.ThrunBreakerOfSilenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return sourcePermanent.getLogName() + " can't be the target of nongreen spells you control or abilities from nongreen sources you control";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card targetCard = game.getCard(event.getTargetId());
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (targetCard != null && stackObject != null && targetCard.getId().equals(source.getSourceId())) {
            if (stackObject instanceof Ability) {
                if (!((Ability) stackObject).getSourceObject(game).getColor(game).isGreen()) {
                    return (!stackObject.isControlledBy(source.getControllerId()));
                }
            }
            else if (!stackObject.getColor(game).isGreen()) {
                return (!stackObject.isControlledBy(source.getControllerId()));
            }
        }
        return false;
    }
}
