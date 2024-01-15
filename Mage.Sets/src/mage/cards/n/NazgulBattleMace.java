package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class NazgulBattleMace extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public NazgulBattleMace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.EQUIPMENT);
        // Equipped creature has menace, deathtouch, annihilator 1, and
        // "Whenever an opponent sacrifices a nontoken permanent, put that card onto the battlefield under your control unless that player pays 3 life."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(new MenaceAbility(false), AttachmentType.EQUIPMENT));
        ability.addEffect(new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT).setText(", deathtouch"));
        ability.addEffect(new GainAbilityAttachedEffect(new AnnihilatorAbility(1), AttachmentType.EQUIPMENT).setText(", annihilator 1"));
        Ability sacTriggerAbility = new SacrificePermanentTriggeredAbility(Zone.BATTLEFIELD,
                new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(
                        new ReturnToBattlefieldUnderYourControlTargetEffect(),
                        new PayLifeCost(3)
                ).setText("put that card onto the battlefield under your control unless that player pays 3 life"),
                filter, TargetController.OPPONENT, SetTargetPointer.PERMANENT, false
        );
        ability.addEffect(new GainAbilityAttachedEffect(sacTriggerAbility, AttachmentType.EQUIPMENT)
                .setText(", and \"" + sacTriggerAbility.getRule() + "\"")
        );
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private NazgulBattleMace(final NazgulBattleMace card) {
        super(card);
    }

    @Override
    public NazgulBattleMace copy() {
        return new NazgulBattleMace(this);
    }
}
