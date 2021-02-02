package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class PitKeeper extends CardImpl {

    public PitKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Pit Keeper enters the battlefield, if you have four or more creature cards in your graveyard, you may return target creature card from your graveyard to your hand.
        TriggeredAbility triggeredAbility = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), true);
        triggeredAbility.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                triggeredAbility,
                new CreatureCardsInControllerGraveyardCondition(4),
                "When {this} enters the battlefield, if you have four or more creature cards in your graveyard, you may return target creature card from your graveyard to your hand."));
    }

    private PitKeeper(final PitKeeper card) {
        super(card);
    }

    @Override
    public PitKeeper copy() {
        return new PitKeeper(this);
    }
}

class CreatureCardsInControllerGraveyardCondition implements Condition {

    private int value;

    public CreatureCardsInControllerGraveyardCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player p = game.getPlayer(source.getControllerId());
        if (p != null && p.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) >= value) {
            return true;
        }
        return false;
    }
}
