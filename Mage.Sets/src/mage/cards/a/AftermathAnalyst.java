package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AftermathAnalyst extends CardImpl {

    public AftermathAnalyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Aftermath Analyst enters the battlefield, mill three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));

        // {3}{G}, Sacrifice Aftermath Analyst: Return all land cards from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(new AftermathAnalystEffect(), new ManaCostsImpl<>("{3}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private AftermathAnalyst(final AftermathAnalyst card) {
        super(card);
    }

    @Override
    public AftermathAnalyst copy() {
        return new AftermathAnalyst(this);
    }
}

class AftermathAnalystEffect extends OneShotEffect {

    AftermathAnalystEffect() {
        super(Outcome.Benefit);
        staticText = "return all land cards from your graveyard to the battlefield tapped";
    }

    private AftermathAnalystEffect(final AftermathAnalystEffect effect) {
        super(effect);
    }

    @Override
    public AftermathAnalystEffect copy() {
        return new AftermathAnalystEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(
                player.getGraveyard().getCards(StaticFilters.FILTER_CARD_LAND, game),
                Zone.BATTLEFIELD, source, game, true, false, false, null
        );
    }
}
