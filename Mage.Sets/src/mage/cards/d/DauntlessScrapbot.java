package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DauntlessScrapbot extends CardImpl {

    public DauntlessScrapbot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature enters, exile each opponent's graveyard. Create a Lander token.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ExileGraveyardAllPlayersEffect(StaticFilters.FILTER_CARD, TargetController.OPPONENT)
                        .setText("exile each opponent's graveyard")
        );
        ability.addEffect(new CreateTokenEffect(new LanderToken()));
        this.addAbility(ability);
    }

    private DauntlessScrapbot(final DauntlessScrapbot card) {
        super(card);
    }

    @Override
    public DauntlessScrapbot copy() {
        return new DauntlessScrapbot(this);
    }
}
