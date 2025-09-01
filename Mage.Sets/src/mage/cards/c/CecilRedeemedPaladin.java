package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author balazskristof
 */
public final class CecilRedeemedPaladin extends CardImpl {

    public CecilRedeemedPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.color.setWhite(true);
        this.nightCard = true;

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Protect -- Whenever Cecil attacks, other attacking creatures gain indestructible until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityAllEffect(
            IndestructibleAbility.getInstance(), Duration.EndOfTurn,
            StaticFilters.FILTER_ATTACKING_CREATURES, true
        )).withFlavorWord("Protect"));
    }

    private CecilRedeemedPaladin(final CecilRedeemedPaladin card) {
        super(card);
    }

    @Override
    public CecilRedeemedPaladin copy() {
        return new CecilRedeemedPaladin(this);
    }
}
