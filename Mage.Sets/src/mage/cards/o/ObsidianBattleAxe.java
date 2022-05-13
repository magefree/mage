package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ObsidianBattleAxe extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.WARRIOR, "a Warrior creature");

    public ObsidianBattleAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1 and has haste.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 1));
        ability.addEffect(new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has haste"));
        this.addAbility(ability);
        // Whenever a Warrior creature enters the battlefield, you may attach Obsidian Battle-Axe to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "attach {this} to it"),
                filter, true, SetTargetPointer.PERMANENT, null));
        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private ObsidianBattleAxe(final ObsidianBattleAxe card) {
        super(card);
    }

    @Override
    public ObsidianBattleAxe copy() {
        return new ObsidianBattleAxe(this);
    }
}
