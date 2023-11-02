package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class WarrenPilferers extends CardImpl {

    public WarrenPilferers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Warren Pilferers enters the battlefield, return target creature card from your graveyard to your hand. If that card is a Goblin card, Warren Pilferers gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new WarrenPilferersReturnEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private WarrenPilferers(final WarrenPilferers card) {
        super(card);
    }

    @Override
    public WarrenPilferers copy() {
        return new WarrenPilferers(this);
    }
}

class WarrenPilferersReturnEffect extends OneShotEffect {

    public WarrenPilferersReturnEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return target creature card from your graveyard to your hand. If that card is a Goblin card, Warren Pilferers gains haste until end of turn";
    }

    private WarrenPilferersReturnEffect(final WarrenPilferersReturnEffect effect) {
        super(effect);
    }

    @Override
    public WarrenPilferersReturnEffect copy() {
        return new WarrenPilferersReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null
                && controller != null
                && controller.moveCards(card, Zone.HAND, source, game)) {
            if (card.hasSubtype(SubType.GOBLIN, game)) {
                game.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }

}
