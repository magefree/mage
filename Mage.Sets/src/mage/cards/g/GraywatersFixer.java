package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraywatersFixer extends CardImpl {

    public GraywatersFixer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Each outlaw creature card in your graveyard has encore {X}, where X is its mana value.
        this.addAbility(new SimpleStaticAbility(new GraywatersFixerEffect()));
    }

    private GraywatersFixer(final GraywatersFixer card) {
        super(card);
    }

    @Override
    public GraywatersFixer copy() {
        return new GraywatersFixer(this);
    }
}

class GraywatersFixerEffect extends ContinuousEffectImpl {
    GraywatersFixerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "each outlaw creature card in your graveyard has encore {X}, where X is its mana value";
    }

    private GraywatersFixerEffect(final GraywatersFixerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getGraveyard().getCards(game)) {
            if (!card.isCreature(game) || !card.isOutlaw(game)) {
                continue;
            }
            UnearthAbility ability = new UnearthAbility(new GenericManaCost(card.getManaValue()));
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public GraywatersFixerEffect copy() {
        return new GraywatersFixerEffect(this);
    }
}
