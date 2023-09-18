package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeechGauntlet extends CardImpl {

    public LeechGauntlet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.LEECH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Equipped creature has lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Reconfigure {4}
        this.addAbility(new ReconfigureAbility("{4}"));
    }

    private LeechGauntlet(final LeechGauntlet card) {
        super(card);
    }

    @Override
    public LeechGauntlet copy() {
        return new LeechGauntlet(this);
    }
}
