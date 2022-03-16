
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CatSoldierCreatureToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class BrimazKingOfOreskos extends CardImpl {

    public BrimazKingOfOreskos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT, SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Brimaz, King of Oreskos attacks, create a 1/1 white Cat Soldier creature token with vigilance attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new CatSoldierCreatureToken(), 1, false, true), false));

        // Whenever Brimaz blocks a creature, create a 1/1 white Cat Soldier creature token with vigilance blocking that creature.
        this.addAbility(new BlocksSourceTriggeredAbility(new BrimazKingOfOreskosEffect(), false, true));
    }

    private BrimazKingOfOreskos(final BrimazKingOfOreskos card) {
        super(card);
    }

    @Override
    public BrimazKingOfOreskos copy() {
        return new BrimazKingOfOreskos(this);
    }
}

class BrimazKingOfOreskosEffect extends OneShotEffect {

    public BrimazKingOfOreskosEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a 1/1 white Cat Soldier creature token with vigilance blocking that creature";
    }

    public BrimazKingOfOreskosEffect(final BrimazKingOfOreskosEffect effect) {
        super(effect);
    }

    @Override
    public BrimazKingOfOreskosEffect copy() {
        return new BrimazKingOfOreskosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        Token token = new CatSoldierCreatureToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (attackingCreature == null || game.getState().getCombat() == null) { return true; }

        // Possible ruling (see Aetherplasm)
        // The token you created is blocking the attacking creature,
        // even if the block couldn't legally be declared (for example, if that creature
        // enters the battlefield tapped, or it can't block, or the attacking creature
        // has protection from it)
        CombatGroup combatGroup = game.getState().getCombat().findGroup(attackingCreature.getId());
        if (combatGroup == null) { return true; }

        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent catToken = game.getPermanent(tokenId);
            if (catToken == null) { continue; }

            combatGroup.addBlocker(tokenId, source.getControllerId(), game);
            game.getCombat().addBlockingGroup(tokenId, attackingCreature.getId(), controller.getId(), game);
        }
        combatGroup.pickBlockerOrder(attackingCreature.getControllerId(), game);

        return true;
    }
}
