
package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.StanggTwinToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth & L_J
 */
public final class Stangg extends CardImpl {

    public Stangg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Stangg enters the battlefield, create a legendary 3/4 red and green Human Warrior creature token named Stangg Twin.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StanggCreateTokenEffect(), false));

        // When Stangg leaves the battlefield, exile that token. 
        // When that token leaves the battlefield, sacrifice Stangg.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new StanggExileTokenEffect(), false);
        ability.addEffect(new InfoEffect("When that token leaves the battlefield, sacrifice {this}"));
        this.addAbility(ability);

    }

    private Stangg(final Stangg card) {
        super(card);
    }

    @Override
    public Stangg copy() {
        return new Stangg(this);
    }
}

class StanggCreateTokenEffect extends OneShotEffect {

    public StanggCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create Stangg Twin, a legendary 3/4 red and green Human Warrior creature token";
    }

    public StanggCreateTokenEffect(final StanggCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new StanggTwinToken());
            effect.apply(game, source);
            game.getState().setValue(source.getSourceId() + "_token", effect.getLastAddedTokenIds());
            for (UUID addedTokenId : effect.getLastAddedTokenIds()) {
                Effect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + sourceObject.getName());
                sacrificeEffect.setTargetPointer(new FixedTarget(sourceObject, game));
                LeavesBattlefieldTriggeredAbility triggerAbility = new LeavesBattlefieldTriggeredAbility(sacrificeEffect, false);
                ContinuousEffect continuousEffect = new GainAbilityTargetEffect(triggerAbility, Duration.WhileOnBattlefield);
                continuousEffect.setTargetPointer(new FixedTarget(addedTokenId, game));
                game.addEffect(continuousEffect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public StanggCreateTokenEffect copy() {
        return new StanggCreateTokenEffect(this);
    }
}

class StanggExileTokenEffect extends OneShotEffect {

    public StanggExileTokenEffect() {
        super(Outcome.Removal);
        staticText = "exile that token";
    }

    public StanggExileTokenEffect(final StanggExileTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> tokenIds = (ArrayList<UUID>) game.getState().getValue(source.getSourceId() + "_token");
            if (tokenIds != null) {
                Cards cards = new CardsImpl();
                for (UUID tokenId : tokenIds) {
                    Permanent tokenPermanent = game.getPermanent(tokenId);
                    if (tokenPermanent != null) {
                        cards.add(tokenPermanent);
                    }
                }
                controller.moveCards(cards, Zone.EXILED, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public StanggExileTokenEffect copy() {
        return new StanggExileTokenEffect(this);
    }
}
