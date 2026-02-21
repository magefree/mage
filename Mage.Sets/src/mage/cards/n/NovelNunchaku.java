package mage.cards.n;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class NovelNunchaku extends CardImpl {

    public NovelNunchaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, attach it to target creature you control. When you do, equipped creature fights up to one target creature an opponent controls.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new FightTargetSourceEffect().setText("when you do, equipped creature fights up to one target creature an opponent controls"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // Equipped creature gets +1/+1 and has trample.
        Ability ability2 = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability2.addEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has trample"));
        this.addAbility(ability2);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private NovelNunchaku(final NovelNunchaku card) {
        super(card);
    }

    @Override
    public NovelNunchaku copy() {
        return new NovelNunchaku(this);
    }
}
