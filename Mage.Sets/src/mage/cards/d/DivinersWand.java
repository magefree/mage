package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DivinersWand extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.WIZARD, "a Wizard creature");

    public DivinersWand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "Whenever you draw a card, this creature gets +1/+1 and gains flying until end of turn" and "{4}: Draw a card."
        // new abilities
        Ability newBoost = new DrawCardControllerTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false);
        newBoost.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).concatBy("and"));
        Ability newDraw = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(4));
        // gain new abilities
        Effect effectBoost = new GainAbilityAttachedEffect(newBoost, AttachmentType.EQUIPMENT)
                .setText("Equipped creature has \"Whenever you draw a card, this creature gets +1/+1 and gains flying until end of turn\"");
        Effect effectDraw = new GainAbilityAttachedEffect(newDraw, AttachmentType.EQUIPMENT)
                .setText("\"{4}: Draw a card.\"");
        // total ability
        Ability totalAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, effectBoost);
        totalAbility.addEffect(effectDraw.concatBy("and"));
        this.addAbility(totalAbility);

        // Whenever a Wizard creature enters the battlefield, you may attach Diviner's Wand to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {this} to it"),
                filter, true, SetTargetPointer.PERMANENT, null));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3), false));
    }

    private DivinersWand(final DivinersWand card) {
        super(card);
    }

    @Override
    public DivinersWand copy() {
        return new DivinersWand(this);
    }
}
