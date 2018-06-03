
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class NecromancersCovenant extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Zombies you control");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public NecromancersCovenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{B}{B}");

        // When Necromancer's Covenant enters the battlefield, exile all creature cards from target player's graveyard, then create a 2/2 black Zombie creature token for each card exiled this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NecromancersConvenantEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Zombies you control have lifelink.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    public NecromancersCovenant(final NecromancersCovenant card) {
        super(card);
    }

    @Override
    public NecromancersCovenant copy() {
        return new NecromancersCovenant(this);
    }
}

class NecromancersConvenantEffect extends OneShotEffect {

    public NecromancersConvenantEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "exile all creature cards from target player's graveyard, then create a 2/2 black Zombie creature token for each card exiled this way";
    }

    public NecromancersConvenantEffect(NecromancersConvenantEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        int count = 0;
        for (Card card : player.getGraveyard().getCards(new FilterCreatureCard(), game)) {
            if (card.moveToExile(source.getSourceId(), "Necromancer Covenant", source.getSourceId(), game)) {
                count += 1;
            }
        }
        ZombieToken zombieToken = new ZombieToken();
        if (zombieToken.putOntoBattlefield(count, game, source.getSourceId(), source.getControllerId())) {
            return true;
        }
        return false;
    }

    @Override
    public NecromancersConvenantEffect copy() {
        return new NecromancersConvenantEffect(this);
    }
}
