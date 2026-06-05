package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class LukeCagePowerMan extends CardImpl {

    public LukeCagePowerMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Unbreakable Skin -- Whenever Luke Cage attacks alone, he gets +2/+0 and gains indestructible until end of turn.
        Ability ability = new AttacksAloneSourceTriggeredAbility(
            new BoostSourceEffect(2, 0, Duration.EndOfTurn).setText("he gets +2/+0")
        );
        ability.addEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
            .setText("and gains indestructible until end of turn"));
        this.addAbility(ability.withFlavorWord("Unbreakable Skin"));
    }

    private LukeCagePowerMan(final LukeCagePowerMan card) {
        super(card);
    }

    @Override
    public LukeCagePowerMan copy() {
        return new LukeCagePowerMan(this);
    }
}
