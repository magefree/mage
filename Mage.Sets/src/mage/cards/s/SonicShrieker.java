package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SonicShrieker extends CardImpl {

    public SonicShrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}{B}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, it deals 2 damage to any target and you gain 2 life. If a player is dealt damage this way, they discard a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SonicShriekerEffect());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SonicShrieker(final SonicShrieker card) {
        super(card);
    }

    @Override
    public SonicShrieker copy() {
        return new SonicShrieker(this);
    }
}

class SonicShriekerEffect extends OneShotEffect {

    SonicShriekerEffect() {
        super(Outcome.Benefit);
        staticText = "it deals 2 damage to any target and you gain 2 life. " +
                "If a player is dealt damage this way, they discard a card";
    }

    private SonicShriekerEffect(final SonicShriekerEffect effect) {
        super(effect);
    }

    @Override
    public SonicShriekerEffect copy() {
        return new SonicShriekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = dealDamage(getTargetPointer().getFirst(game, source), game, source);
        Optional.ofNullable(source.getControllerId())
                .map(game::getPlayer)
                .ifPresent(controller -> controller.gainLife(2, game, source));
        if (player != null) {
            player.discard(1, false, false, source, game);
        }
        return true;
    }

    private static Player dealDamage(UUID targetId, Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            permanent.damage(2, source, game);
            return null;
        }
        Player player = game.getPlayer(targetId);
        if (player != null && player.damage(2, source, game) > 0) {
            return player;
        }
        return null;
    }
}
