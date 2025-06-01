package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KainTraitorousDragoon extends CardImpl {

    public KainTraitorousDragoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Jump -- During your turn, Kain has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                MyTurnCondition.instance, "during your turn, {this} has flying"
        )).withFlavorWord("Jump"));

        // Whenever Kain deals combat damage to a player, that player gains control of Kain. If they do, you draw that many cards, create that many tapped Treasure tokens, then lose that much life.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new KainTraitorousDragoonEffect(), false, true
        ));
    }

    private KainTraitorousDragoon(final KainTraitorousDragoon card) {
        super(card);
    }

    @Override
    public KainTraitorousDragoon copy() {
        return new KainTraitorousDragoon(this);
    }
}

class KainTraitorousDragoonEffect extends OneShotEffect {

    KainTraitorousDragoonEffect() {
        super(Outcome.Benefit);
        staticText = "that player gains control of {this}. If they do, you draw that many cards, " +
                "create that many tapped Treasure tokens, then lose that much life";
    }

    private KainTraitorousDragoonEffect(final KainTraitorousDragoonEffect effect) {
        super(effect);
    }

    @Override
    public KainTraitorousDragoonEffect copy() {
        return new KainTraitorousDragoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, player.getId()
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        game.processAction();
        if (!permanent.isControlledBy(player.getId())) {
            return false;
        }
        int amount = (Integer) getValue("damage");
        return amount > 0
                && Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(controller -> {
                    controller.drawCards(amount, source, game);
                    new TreasureToken().putOntoBattlefield(
                            amount, game, source, controller.getId(), true, false
                    );
                    controller.loseLife(amount, game, source, false);
                    return true;
                })
                .orElse(false);
    }
}
