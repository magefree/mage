package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class LifeDeath extends SplitCard {

    public LifeDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}", "{1}{B}", SpellAbilityType.SPLIT);

        // Life
        // All lands you control become 1/1 creatures until end of turn. They're still lands.
        getLeftHalfCard().getSpellAbility().addEffect(new BecomesCreatureAllEffect(
                new CreatureToken(1, 1),
                "lands", StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, Duration.EndOfTurn, false)
                .setText("all lands you control become 1/1 creatures until end of turn. They're still lands"));

        // Death
        // Return target creature card from your graveyard to the battlefield. You lose life equal to its converted mana cost.
        Target target = new TargetCardInYourGraveyard(1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        getRightHalfCard().getSpellAbility().addTarget(target);
        getRightHalfCard().getSpellAbility().addEffect(new DeathEffect());

    }

    private LifeDeath(final LifeDeath card) {
        super(card);
    }

    @Override
    public LifeDeath copy() {
        return new LifeDeath(this);
    }
}

class DeathEffect extends OneShotEffect {

    public DeathEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card from your graveyard to the battlefield. You lose life equal to its mana value";
    }

    private DeathEffect(final DeathEffect effect) {
        super(effect);
    }

    @Override
    public DeathEffect copy() {
        return new DeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card creatureCard = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (creatureCard != null && controller != null) {
            boolean result = false;
            if (game.getState().getZone(creatureCard.getId()) == Zone.GRAVEYARD) {
                controller.moveCards(creatureCard, Zone.BATTLEFIELD, source, game);
            }
            controller.loseLife(creatureCard.getManaValue(), game, source, false);
            return true;
        }
        return false;
    }
}
