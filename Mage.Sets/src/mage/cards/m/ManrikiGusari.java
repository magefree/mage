package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
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
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ManrikiGusari extends CardImpl {

    public ManrikiGusari(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);
        // Equipped creature gets +1/+2 and has "{tap}: Destroy target Equipment."
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 2)));
        Ability gainedAbility = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        gainedAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT)));
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    private ManrikiGusari(final ManrikiGusari card) {
        super(card);
    }

    @Override
    public ManrikiGusari copy() {
        return new ManrikiGusari(this);
    }
}
