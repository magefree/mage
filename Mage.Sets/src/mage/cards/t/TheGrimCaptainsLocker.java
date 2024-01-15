package mage.cards.t;

import java.util.Objects;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

public class TheGrimCaptainsLocker extends CardImpl {

    public TheGrimCaptainsLocker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}, Surveil 1.
        this.addAbility(new SimpleActivatedAbility(new SurveilEffect(1), new TapSourceCost()));

        // {T}: Until end of turn, each creature card in your graveyard gains "Escape &mdash; {3}{B}, Exile four other cards from your graveyard."
        Ability ability = new SimpleActivatedAbility(new TheGrimCaptainsLockerEffect(), new TapSourceCost());
        this.addAbility(ability);

    }

    private TheGrimCaptainsLocker(final TheGrimCaptainsLocker card) {
        super(card);
    }

    @Override
    public TheGrimCaptainsLocker copy() {
        return new TheGrimCaptainsLocker(this);
    }
}

class TheGrimCaptainsLockerEffect extends ContinuousEffectImpl {

    TheGrimCaptainsLockerEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, each creature card in your graveyard gains " +
                "\"Escape &mdash; {3}{B}, Exile four other cards from your graveyard.\"";
    }

    private TheGrimCaptainsLockerEffect(final TheGrimCaptainsLockerEffect effect) {
        super(effect);
    }

    @Override
    public TheGrimCaptainsLockerEffect copy() {
        return new TheGrimCaptainsLockerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.isCreature(game))
                .forEach(card -> {
                    Ability ability = new EscapeAbility(card, "{3}{B}", 4);
                    ability.setSourceId(card.getId());
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                });
        return true;
    }
}
