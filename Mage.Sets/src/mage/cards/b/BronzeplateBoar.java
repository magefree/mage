package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ReconfigureAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BronzeplateBoar extends CardImpl {

    public BronzeplateBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Equipped creature gets +3/+2 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Reconfigure {5}
        this.addAbility(new ReconfigureAbility("{5}"));
    }

    private BronzeplateBoar(final BronzeplateBoar card) {
        super(card);
    }

    @Override
    public BronzeplateBoar copy() {
        return new BronzeplateBoar(this);
    }
}
