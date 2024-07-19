package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.OffspringAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgateInstigator extends CardImpl {

    public AgateInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Offspring {1}{R}
        this.addAbility(new OffspringAbility("{1}{R}"));

        // Whenever another creature you control enters, this creature deals 1 damage to each opponent.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), StaticFilters.FILTER_ANOTHER_CREATURE
        ));
    }

    private AgateInstigator(final AgateInstigator card) {
        super(card);
    }

    @Override
    public AgateInstigator copy() {
        return new AgateInstigator(this);
    }
}
