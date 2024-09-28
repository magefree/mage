
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class MagusOfTheWill extends CardImpl {

    public MagusOfTheWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{B}, {T}, Exile Magus of the Will: Until end of turn, you may play cards from your graveyard.
        // If a card would be put into your graveyard from anywhere else this turn, exile that card instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CanPlayCardsFromGraveyardEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addEffect(new GraveyardFromAnywhereExileReplacementEffect(Duration.EndOfTurn));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private MagusOfTheWill(final MagusOfTheWill card) {
        super(card);
    }

    @Override
    public MagusOfTheWill copy() {
        return new MagusOfTheWill(this);
    }
}

class CanPlayCardsFromGraveyardEffect extends ContinuousEffectImpl {

    CanPlayCardsFromGraveyardEffect() {
        this(Duration.EndOfTurn);
    }

    public CanPlayCardsFromGraveyardEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, you may play cards from your graveyard";
    }

    private CanPlayCardsFromGraveyardEffect(final CanPlayCardsFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public CanPlayCardsFromGraveyardEffect copy() {
        return new CanPlayCardsFromGraveyardEffect(this);
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
