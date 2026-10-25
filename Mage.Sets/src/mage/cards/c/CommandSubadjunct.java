package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class CommandSubadjunct extends CardImpl {

    public CommandSubadjunct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");
        
        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Artifact creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private CommandSubadjunct(final CommandSubadjunct card) {
        super(card);
    }

    @Override
    public CommandSubadjunct copy() {
        return new CommandSubadjunct(this);
    }
}
