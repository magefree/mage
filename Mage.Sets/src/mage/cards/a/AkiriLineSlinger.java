package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class AkiriLineSlinger extends CardImpl {

    public AkiriLineSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Akiri, Line-Slinger gets +1/+0 for each artifact you control.
        Effect effect = new BoostSourceEffect(ArtifactYouControlCount.instance, StaticValue.get(0), Duration.WhileOnBattlefield);
        effect.setText("{this} gets +1/+0 for each artifact you control");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect)
                .addHint(ArtifactYouControlHint.instance));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private AkiriLineSlinger(final AkiriLineSlinger card) {
        super(card);
    }

    @Override
    public AkiriLineSlinger copy() {
        return new AkiriLineSlinger(this);
    }
}
