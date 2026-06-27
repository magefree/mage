package mage.cards.m;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MyPrecious extends AdventureCard {

    public MyPrecious(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, new CardType[]{CardType.INSTANT}, "{3}", "Allure of Power", "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has hexproof and can't be blocked.
        Ability ability = new SimpleStaticAbility(
            new GainAbilityAttachedEffect(HexproofAbility.getInstance(), AttachmentType.EQUIPMENT)
        );
        ability.addEffect(
            new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT).setText("and can't be blocked")
        );
        this.addAbility(ability);

        // Equip - {2}, Pay 2 life.
        ability = new EquipAbility(2);
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);

        // Allure of Power
        // As an additional cost to cast this spell, sacrifice a creature.
        // Draw two cards.
        this.getSpellCard().getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        this.finalizeAdventure();
    }

    private MyPrecious(final MyPrecious card) {
        super(card);
    }

    @Override
    public MyPrecious copy() {
        return new MyPrecious(this);
    }
}
