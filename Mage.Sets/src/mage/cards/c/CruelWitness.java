package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class CruelWitness extends CardImpl {

    public CruelWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CruelWitnessEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));
    }

    private CruelWitness(final CruelWitness card) {
        super(card);
    }

    @Override
    public CruelWitness copy() {
        return new CruelWitness(this);
    }
}

class CruelWitnessEffect extends OneShotEffect {

    public CruelWitnessEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may put that card into your graveyard";
    }

    private CruelWitnessEffect(final CruelWitnessEffect effect) {
        super(effect);
    }

    @Override
    public CruelWitnessEffect copy() {
        return new CruelWitnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card topCard = controller.getLibrary().getFromTop(game);
            if (topCard != null) {
                controller.lookAtCards("Top card of your library", topCard, game);
                if (controller.chooseUse(Outcome.AIDontUseIt, "Put the top card of your library into your graveyard?", source, game)) {
                    controller.moveCards(topCard, Zone.GRAVEYARD, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
