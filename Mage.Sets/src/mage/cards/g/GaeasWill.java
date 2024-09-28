package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class GaeasWill extends CardImpl {

    public GaeasWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setGreen(true);

        // Suspend 4—{G}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{G}"), this));

        // Until end of turn, you may play land cards and cast spells from your graveyard.
        this.getSpellAbility().addEffect(new GaeasWillGraveyardEffect());

        // If a card would be put into your graveyard from anywhere this turn, exile that card instead.
        this.addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(Duration.EndOfTurn, null, true, false)));
    }

    private GaeasWill(final GaeasWill card) {
        super(card);
    }

    @Override
    public GaeasWill copy() {
        return new GaeasWill(this);
    }
}

class GaeasWillGraveyardEffect extends ContinuousEffectImpl {

    GaeasWillGraveyardEffect() {
        this(Duration.EndOfTurn);
    }

    public GaeasWillGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        this.staticText = "Until end of turn, you may play lands and cast spells from your graveyard";
    }

    private GaeasWillGraveyardEffect(final GaeasWillGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public GaeasWillGraveyardEffect copy() {
        return new GaeasWillGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setPlayCardsFromGraveyard(true);
            return true;
        }
        return false;
    }
}
