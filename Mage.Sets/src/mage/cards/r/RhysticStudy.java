package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class RhysticStudy extends CardImpl {

    public RhysticStudy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever an opponent casts a spell, you may draw a card unless that player pays {1}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new RhysticStudyDrawEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER));
    }

    private RhysticStudy(final RhysticStudy card) {
        super(card);
    }

    @Override
    public RhysticStudy copy() {
        return new RhysticStudy(this);
    }
}

class RhysticStudyDrawEffect extends OneShotEffect {

    public RhysticStudyDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may draw a card unless that player pays {1}";
    }

    public RhysticStudyDrawEffect(final RhysticStudyDrawEffect effect) {
        super(effect);
    }

    @Override
    public RhysticStudyDrawEffect copy() {
        return new RhysticStudyDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && opponent != null && sourceObject != null) {
            if (controller.chooseUse(Outcome.DrawCard, "Draw a card (" + sourceObject.getLogName() + ')', source, game)) {
                Cost cost = ManaUtil.createManaCost(1, false);
                if (opponent.chooseUse(Outcome.Benefit, "Pay {1}?", source, game)
                        && cost.pay(source, game, source, opponent.getId(), false, null)) {
                    return true;
                }
                controller.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }

}
