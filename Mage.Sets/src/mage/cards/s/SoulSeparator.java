
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken2;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class SoulSeparator extends CardImpl {

    public SoulSeparator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {5}, {T}, Sacrifice Soul Separator: Exile target creature card from your graveyard.
        // Create a token that's a copy of that card except it's 1/1, it's a Spirit in addition to its other types, and it has flying.
        // Put a black Zombie creature token onto the battlefield with power equal to that card's power and toughness equal that card's toughness.

        // 20200601 - 701.6c
        // Previously, an effect that created tokens instructed a player to “put [those tokens] onto the battlefield.”
        // Cards that were printed with that text have received errata in the Oracle card reference so they now
        // “create” those tokens.

        CreateTokenCopyTargetEffect copyEffect = new CreateTokenCopyTargetEffect(null, null, false, 1, false, false, null, 1, 1, true);
        copyEffect.setAdditionalSubType(SubType.SPIRIT);
        copyEffect.setText("Exile target creature card from your graveyard. Create a token that's a copy of that card, except it's 1/1, it's a Spirit in addition to its other types, and it has flying");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, copyEffect, new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addEffect(new SoulSeparatorEffect());
        this.addAbility(ability);
    }

    private SoulSeparator(final SoulSeparator card) {
        super(card);
    }

    @Override
    public SoulSeparator copy() {
        return new SoulSeparator(this);
    }
}

class SoulSeparatorEffect extends OneShotEffect {

    public SoulSeparatorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a black Zombie creature token with power equal to that card's power and toughness equal to that card's toughness";
    }

    public SoulSeparatorEffect(final SoulSeparatorEffect effect) {
        super(effect);
    }

    @Override
    public SoulSeparatorEffect copy() {
        return new SoulSeparatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card creatureCard = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (creatureCard != null && controller != null) {
            boolean result = false;
            if (game.getState().getZone(creatureCard.getId()) == Zone.GRAVEYARD) {
                result = controller.moveCardToExileWithInfo(creatureCard, null, "", source, game, Zone.GRAVEYARD, true);
                ZombieToken2 token = new ZombieToken2(creatureCard.getPower().getValue(), creatureCard.getToughness().getValue());
                token.putOntoBattlefield(1, game, source, source.getControllerId());
            }
            return result;
        }
        return false;
    }
}
