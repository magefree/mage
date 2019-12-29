package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.YoungPyromancerElementalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScamperingScorcher extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.ELEMENTAL, "Elementals");

    public ScamperingScorcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Scampering Scorcher enters the battlefield, create two 1/1 red Elemental creature tokens. Elementals you control gain haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new YoungPyromancerElementalToken(), 2)
        );
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, filter
        ));
        this.addAbility(ability);
    }

    private ScamperingScorcher(final ScamperingScorcher card) {
        super(card);
    }

    @Override
    public ScamperingScorcher copy() {
        return new ScamperingScorcher(this);
    }
}
