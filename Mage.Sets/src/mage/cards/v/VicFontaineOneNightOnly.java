package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class VicFontaineOneNightOnly extends CardImpl {

    public VicFontaineOneNightOnly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Seek out -- Whenever you draw your second card each turn, one or two target creatures each get +2/+0 and gain lifelink until end of turn.
        Ability ability = new DrawNthCardTriggeredAbility(new BoostTargetEffect(2, 0)
            .setText("one or two target creatures each get +2/+0"))
            .withFlavorWord("Seek out");
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
            .setText("and gain lifelink until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(1, 2));
        this.addAbility(ability);
    }

    private VicFontaineOneNightOnly(final VicFontaineOneNightOnly card) {
        super(card);
    }

    @Override
    public VicFontaineOneNightOnly copy() {
        return new VicFontaineOneNightOnly(this);
    }
}
