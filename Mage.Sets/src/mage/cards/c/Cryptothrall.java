package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class Cryptothrall extends CardImpl {

    public Cryptothrall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protector -- Other artifact creatures you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE,true
        )).withFlavorWord("Protector"));
    }

    private Cryptothrall(final Cryptothrall card) {
        super(card);
    }

    @Override
    public Cryptothrall copy() {
        return new Cryptothrall(this);
    }
}
