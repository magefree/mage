package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.keyword.DemonstrateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HealingTechnique extends CardImpl {

    public HealingTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");


        // Demonstrate
        this.addAbility(new DemonstrateAbility());

        // Return target card from your graveyard to your hand. You gain life equal to that card's mana value. Exile Healing Technique.
        this.getSpellAbility().addEffect(new HealingTechniqueEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
    }

    private HealingTechnique(final HealingTechnique card) {
        super(card);
    }

    @Override
    public HealingTechnique copy() {
        return new HealingTechnique(this);
    }
}

class HealingTechniqueEffect extends OneShotEffect {

    HealingTechniqueEffect() {
        super(Outcome.Benefit);
        staticText = "return target card from your graveyard to your hand. " +
                "You gain life equal to that card's mana value";
    }

    private HealingTechniqueEffect(final HealingTechniqueEffect effect) {
        super(effect);
    }

    @Override
    public HealingTechniqueEffect copy() {
        return new HealingTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        int manaValue = card.getManaValue();
        player.moveCards(card, Zone.HAND, source, game);
        player.gainLife(manaValue, game, source);
        return true;
    }
}
