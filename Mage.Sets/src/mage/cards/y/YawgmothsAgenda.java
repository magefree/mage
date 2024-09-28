
package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class YawgmothsAgenda extends CardImpl {

    public YawgmothsAgenda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // You can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(new CantCastMoreThanOneSpellEffect(TargetController.YOU)));
        // You may play cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(new YawgmothsAgendaCanPlayCardsFromGraveyardEffect()));
        // If a card would be put into your graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(true, false)));
    }

    private YawgmothsAgenda(final YawgmothsAgenda card) {
        super(card);
    }

    @Override
    public YawgmothsAgenda copy() {
        return new YawgmothsAgenda(this);
    }
}

class YawgmothsAgendaCanPlayCardsFromGraveyardEffect extends ContinuousEffectImpl {

    YawgmothsAgendaCanPlayCardsFromGraveyardEffect() {
        this(Duration.WhileOnBattlefield);
    }

    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may play cards from your graveyard";
    }

    private YawgmothsAgendaCanPlayCardsFromGraveyardEffect(final YawgmothsAgendaCanPlayCardsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothsAgendaCanPlayCardsFromGraveyardEffect copy() {
        return new YawgmothsAgendaCanPlayCardsFromGraveyardEffect(this);
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
