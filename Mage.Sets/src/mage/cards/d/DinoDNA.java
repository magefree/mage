package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author jimga150
 */
public final class DinoDNA extends CardImpl {

    public DinoDNA(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        

        // Imprint -- {1}, {T}: Exile target creature card from a graveyard. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DinoDNAImprintEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard")));
        // {6}: Create a token that's a copy of target creature card exiled with Dino DNA, except it's a 6/6 green Dinosaur creature with trample. Activate only as a sorcery.

        this.addAbility(ability);
    }

    private DinoDNA(final DinoDNA card) {
        super(card);
    }

    @Override
    public DinoDNA copy() {
        return new DinoDNA(this);
    }
}
class DinoDNAImprintEffect extends OneShotEffect {

    DinoDNAImprintEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target creature card from a graveyard.";
    }

    private DinoDNAImprintEffect(final DinoDNAImprintEffect effect) {
        super(effect);
    }

    @Override
    public DinoDNAImprintEffect copy() {
        return new DinoDNAImprintEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Card toExile = game.getCard(source.getFirstTarget());
        if (toExile == null) {
            return false;
        }

        UUID exileId = CardUtil.getExileZoneId(game, source);
        player.moveCardsToExile(
                toExile, source, game, true,
                exileId, CardUtil.getSourceName(game, source)
        );
        ExileZone exileZone = game.getExile().getExileZone(exileId);
        return exileZone != null && !exileZone.isEmpty();
    }
}
