package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class WebShooters extends CardImpl {

    public WebShooters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 and has reach and "Whenever this creature attacks, tap target creature an opponent controls."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("has reach")
                .concatBy("and")
        );
        Ability gainedAbility = new AttacksTriggeredAbility(new TapTargetEffect());
        gainedAbility.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addEffect(new GainAbilityAttachedEffect(gainedAbility, null)
                .setText("\"Whenever this creature attacks, tap target creature an opponent controls.\"")
                .concatBy("and")
        );
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private WebShooters(final WebShooters card) {
        super(card);
    }

    @Override
    public WebShooters copy() {
        return new WebShooters(this);
    }
}
