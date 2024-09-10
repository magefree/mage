package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class SolitaryCamel extends CardImpl {

    public SolitaryCamel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Solitary Camel has lifelink as long as you control a desert or there is a desert card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()),
                DesertControlledOrGraveyardCondition.instance, "{this} has lifelink as long as " +
                "you control a Desert or there is a Desert card in your graveyard."
        )).addHint(DesertControlledOrGraveyardCondition.getHint()));
    }

    private SolitaryCamel(final SolitaryCamel card) {
        super(card);
    }

    @Override
    public SolitaryCamel copy() {
        return new SolitaryCamel(this);
    }
}
