package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.AdditionalTriggersAttackingReplacementEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WulfgarOfIcewindDale extends CardImpl {

    public WulfgarOfIcewindDale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Melee
        this.addAbility(new MeleeAbility());

        // If a creature you control attacking would cause a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggersAttackingReplacementEffect(true)));
    }

    private WulfgarOfIcewindDale(final WulfgarOfIcewindDale card) {
        super(card);
    }

    @Override
    public WulfgarOfIcewindDale copy() {
        return new WulfgarOfIcewindDale(this);
    }
}
