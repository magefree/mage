
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class RotfeasterMaggot extends CardImpl {

    public RotfeasterMaggot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Rotfeaster Maggot enters the battlefield, exile target creature card from a graveyard. You gain life equal to that card's toughness.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RotfeasterMaggotExileEffect(), false);
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }

    private RotfeasterMaggot(final RotfeasterMaggot card) {
        super(card);
    }

    @Override
    public RotfeasterMaggot copy() {
        return new RotfeasterMaggot(this);
    }
}

class RotfeasterMaggotExileEffect extends OneShotEffect {

    public RotfeasterMaggotExileEffect() {
        super(Outcome.GainLife);
        this.staticText = "exile target creature card from a graveyard. You gain life equal to that card's toughness";
    }

    private RotfeasterMaggotExileEffect(final RotfeasterMaggotExileEffect effect) {
        super(effect);
    }

    @Override
    public RotfeasterMaggotExileEffect copy() {
        return new RotfeasterMaggotExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
            if (targetCard != null) {
                if (game.getState().getZone(targetCard.getId()) == Zone.GRAVEYARD) {
                    controller.moveCardToExileWithInfo(targetCard, null, "", source, game, Zone.GRAVEYARD, true);
                }
                controller.gainLife(targetCard.getToughness().getValue(), game, source);
                return true;
            }
        }
        return false;
    }
}
