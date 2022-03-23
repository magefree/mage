
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class LuminescentRain extends CardImpl {

    public LuminescentRain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose a creature type. You gain 2 life for each permanent you control of that type.
        this.getSpellAbility().addEffect(new LuminescentRainEffect());
    }

    private LuminescentRain(final LuminescentRain card) {
        super(card);
    }

    @Override
    public LuminescentRain copy() {
        return new LuminescentRain(this);
    }
}

class LuminescentRainEffect extends OneShotEffect {

    LuminescentRainEffect() {
        super(Outcome.GainLife);
        this.staticText = "Choose a creature type. You gain 2 life for each permanent you control of that type.";
    }

    LuminescentRainEffect(final LuminescentRainEffect effect) {
        super(effect);
    }

    @Override
    public LuminescentRainEffect copy() {
        return new LuminescentRainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Choice typeChoice = new ChoiceCreatureType(game.getObject(source));
        if (player != null && player.choose(Outcome.BoostCreature, typeChoice, game)) {
            FilterControlledPermanent filter = new FilterControlledPermanent();
            filter.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
            return new GainLifeEffect(new PermanentsOnBattlefieldCount(filter, 2)).apply(game, source);
        }
        return false;
    }
}
