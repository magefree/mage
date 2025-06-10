package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class InfernalVessel extends CardImpl {

    public InfernalVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature dies, if it wasn't a Demon, return it to the battlefield under its owner's control with two +1/+1 counters on it. It's a Demon in addition to its other types.
        this.addAbility(new DiesSourceTriggeredAbility(new InfernalVesselReturnEffect())
                .withInterveningIf(InfernalVesselCondition.instance));
    }

    private InfernalVessel(final InfernalVessel card) {
        super(card);
    }

    @Override
    public InfernalVessel copy() {
        return new InfernalVessel(this);
    }
}

enum InfernalVesselCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "permanentLeftBattlefield", Permanent.class)
                .filter(permanent -> !permanent.hasSubtype(SubType.DEMON, game))
                .isPresent();
    }

    @Override
    public String toString() {
        return "it wasn't a Demon";
    }
}

class InfernalVesselReturnEffect extends OneShotEffect {

    InfernalVesselReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return it to the battlefield under its owner's control with two +1/+1 counters on it. It's a Demon in addition to its other types";
    }

    private InfernalVesselReturnEffect(final InfernalVesselReturnEffect effect) {
        super(effect);
    }

    @Override
    public InfernalVesselReturnEffect copy() {
        return new InfernalVesselReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        OneShotEffect effect = new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(
                CounterType.P1P1.createInstance(2)
        );
        effect.setTargetPointer(new FixedTarget(card, game));
        effect.apply(game, source);
        game.processAction();
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent != null) {
            game.addEffect(new AddCardSubTypeTargetEffect(
                    SubType.DEMON, Duration.Custom
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
