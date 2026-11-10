package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.OfficerToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class MACOStrikeTeam extends CardImpl {

    public MACOStrikeTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a creature you control becomes the target of a spell or ability an opponent controls, create a tapped 1/1 red Officer creature token.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(
            new CreateTokenEffect(new OfficerToken(), 1, true),
            StaticFilters.FILTER_CONTROLLED_A_CREATURE, StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS
        ));
    }

    private MACOStrikeTeam(final MACOStrikeTeam card) {
        super(card);
    }

    @Override
    public MACOStrikeTeam copy() {
        return new MACOStrikeTeam(this);
    }
}
