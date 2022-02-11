package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelcladSpirit extends CardImpl {

    private static final FilterPermanent filter = new FilterEnchantmentPermanent("an enchantment");

    public SteelcladSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever an enchantment enters the battlefield under your control, Steelclad Spirit can attack this turn as though it didn't have defender.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.EndOfTurn), filter
        ));
    }

    private SteelcladSpirit(final SteelcladSpirit card) {
        super(card);
    }

    @Override
    public SteelcladSpirit copy() {
        return new SteelcladSpirit(this);
    }
}
