package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.NettlingNuisancePirateToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;

/**
 *
 * @author Xanderhall
 */
public final class NettlingNuisance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.FAERIE, "Faeries");

    public NettlingNuisance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Faeries you control deal combat damage to a player, that player creates a 4/2 red Pirate creature token with "This creature can't block." The token is goaded for the rest of the game.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(Zone.BATTLEFIELD, new NettlingNuisanceEffect(), filter, SetTargetPointer.PLAYER, false));
    }

    private NettlingNuisance(final NettlingNuisance card) {
        super(card);
    }

    @Override
    public NettlingNuisance copy() {
        return new NettlingNuisance(this);
    }
}

class NettlingNuisanceEffect extends OneShotEffect {

    NettlingNuisanceEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "that player creates a 4/2 red Pirate creature token with \"This creature can't block.\" The token is goaded for the rest of the game. " 
            + "<i>(It attacks each combat if able and attacks a player other than you if able.)</i>";
    }

    private NettlingNuisanceEffect(final NettlingNuisanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        Token token = new NettlingNuisancePirateToken();
        token.putOntoBattlefield(1, game, source, player.getId());
        token.getLastAddedTokenIds().forEach(id -> game.addEffect(
            new GoadTargetEffect().setDuration(Duration.EndOfGame).setTargetPointer(new FixedTarget(id, game)), source
        ));
        return true;
    }

    @Override
    public NettlingNuisanceEffect copy() {
        return new NettlingNuisanceEffect(this);
    }

}
