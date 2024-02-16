package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TectonicReformation extends CardImpl {

    public TectonicReformation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Each land card in your hand has cycling {R}.
        this.addAbility(new SimpleStaticAbility(new TectonicReformationEffect()));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private TectonicReformation(final TectonicReformation card) {
        super(card);
    }

    @Override
    public TectonicReformation copy() {
        return new TectonicReformation(this);
    }
}

class TectonicReformationEffect extends ContinuousEffectImpl {

    TectonicReformationEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "each land card in your hand has cycling {R}";
    }

    private TectonicReformationEffect(final TectonicReformationEffect effect) {
        super(effect);
    }

    @Override
    public TectonicReformationEffect copy() {
        return new TectonicReformationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getHand().getCards(StaticFilters.FILTER_CARD_LAND, game)) {
            game.getState().addOtherAbility(card, new CyclingAbility(new ManaCostsImpl<>("{R}")));
        }
        return true;
    }
}
