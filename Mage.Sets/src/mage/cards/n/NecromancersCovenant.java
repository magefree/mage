package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NecromancersCovenant extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.ZOMBIE, "Zombies you control");

    public NecromancersCovenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{B}{B}");

        // When Necromancer's Covenant enters the battlefield, exile all creature cards from target player's graveyard, then create a 2/2 black Zombie creature token for each card exiled this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NecromancersConvenantEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Zombies you control have lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private NecromancersCovenant(final NecromancersCovenant card) {
        super(card);
    }

    @Override
    public NecromancersCovenant copy() {
        return new NecromancersCovenant(this);
    }
}

class NecromancersConvenantEffect extends OneShotEffect {

    NecromancersConvenantEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "exile all creature cards from target player's graveyard, " +
                "then create a 2/2 black Zombie creature token for each card exiled this way";
    }

    private NecromancersConvenantEffect(NecromancersConvenantEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        int count = cards.size();
        return count > 0 && new ZombieToken().putOntoBattlefield(count, game, source, controller.getId());
    }

    @Override
    public NecromancersConvenantEffect copy() {
        return new NecromancersConvenantEffect(this);
    }
}
