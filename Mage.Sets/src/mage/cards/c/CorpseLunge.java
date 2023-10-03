
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class CorpseLunge extends CardImpl {

    public CorpseLunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // As an additional cost to cast Corpse Lunge, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
        // Corpse Lunge deals damage equal to the exiled card's power to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CorpseLungeEffect());

    }

    private CorpseLunge(final CorpseLunge card) {
        super(card);
    }

    @Override
    public CorpseLunge copy() {
        return new CorpseLunge(this);
    }
}

class CorpseLungeEffect extends OneShotEffect {

    public CorpseLungeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "{this} deals damage equal to the exiled card's power to target creature";
    }

    private CorpseLungeEffect(final CorpseLungeEffect effect) {
        super(effect);
    }

    @Override
    public CorpseLungeEffect copy() {
        return new CorpseLungeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                Card card = game.getCard(cost.getTargets().getFirstTarget());
                if (card != null) {
                    amount = card.getPower().getValue();
                }
                break;
            }
        }
        if (amount > 0) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.damage(amount, source.getSourceId(), source, game, false, true);
                return true;
            }
        }
        return false;
    }
}
