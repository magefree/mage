package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class AssassinGauntlet extends CardImpl {

    public AssassinGauntlet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Assassin Gauntlet enters the battlefield, attach it to up to one target creature you control. Tap all creatures target opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(Outcome.BoostCreature).setText("attach it to up to one target creature you control"));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));

        ability.addEffect(new TapAllTargetPlayerControlsEffect(StaticFilters.FILTER_PERMANENT_CREATURES).setTargetPointer(new SecondTargetPointer()));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Equipped creature gets +1/+1 and has "Whenever this creature deals combat damage to a player, draw a card, then discard a card."
        Ability boostAbility = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        Ability subAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawDiscardControllerEffect());
        boostAbility.addEffect(new GainAbilityAttachedEffect(subAbility, AttachmentType.EQUIPMENT)
                .setText("and has \""+subAbility.getRule("this creature")+"\"").concatBy(""));
        this.addAbility(boostAbility);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private AssassinGauntlet(final AssassinGauntlet card) {
        super(card);
    }

    @Override
    public AssassinGauntlet copy() {
        return new AssassinGauntlet(this);
    }
}
