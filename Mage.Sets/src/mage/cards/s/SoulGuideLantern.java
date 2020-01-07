package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class SoulGuideLantern extends CardImpl {

    public SoulGuideLantern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // When Soul-Guide Lantern enters the battlefield, exile target card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // {T}, Sacrifice Soul-Guide Lantern: Exile each opponent's graveyard.
        ability = new SimpleActivatedAbility(new SoulGuideLanternEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // {1}, {T}, Sacrifice Soul-Guide Lantern: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SoulGuideLantern(final SoulGuideLantern card) {
        super(card);
    }

    @Override
    public SoulGuideLantern copy() {
        return new SoulGuideLantern(this);
    }
}

class SoulGuideLanternEffect extends OneShotEffect {

    SoulGuideLanternEffect() {
        super(Outcome.Benefit);
        staticText = "exile each opponent's graveyard";
    }

    private SoulGuideLanternEffect(final SoulGuideLanternEffect effect) {
        super(effect);
    }

    @Override
    public SoulGuideLanternEffect copy() {
        return new SoulGuideLanternEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(new CardsImpl(
                game.getOpponents(source.getControllerId())
                        .stream()
                        .map(game::getPlayer)
                        .map(Player::getGraveyard)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
        ), Zone.EXILED, source, game);
    }
}