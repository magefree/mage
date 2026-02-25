package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class MoleModule extends CardImpl {

    public MoleModule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever this Vehicle deals combat damage to a player, mill four cards. You may put a permanent card from among them onto the battlefield.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MoleModuleEffect(), false));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private MoleModule(final MoleModule card) {
        super(card);
    }

    @Override
    public MoleModule copy() {
        return new MoleModule(this);
    }
}

class MoleModuleEffect extends OneShotEffect {

    MoleModuleEffect() {
        super(Outcome.Benefit);
        staticText = "mill four cards. You may put a permanent card from among them onto the battlefield";
    }

    private MoleModuleEffect(final MoleModuleEffect effect) {
        super(effect);
    }

    @Override
    public MoleModuleEffect copy() {
        return new MoleModuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(4, source, game);
        TargetCard target = new TargetCard(1, Zone.GRAVEYARD, StaticFilters.FILTER_CARD_PERMANENT);
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}
