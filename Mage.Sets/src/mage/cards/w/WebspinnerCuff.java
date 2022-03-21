package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ReachAbility;
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
public final class WebspinnerCuff extends CardImpl {

    public WebspinnerCuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Equipped creature gets +1/+4 and has reach.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 4));
        ability.addEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has reach"));
        this.addAbility(ability);

        // Reconfigure {4}
        this.addAbility(new ReconfigureAbility("{4}"));
    }

    private WebspinnerCuff(final WebspinnerCuff card) {
        super(card);
    }

    @Override
    public WebspinnerCuff copy() {
        return new WebspinnerCuff(this);
    }
}
