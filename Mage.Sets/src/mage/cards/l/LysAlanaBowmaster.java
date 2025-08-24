package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LysAlanaBowmaster extends CardImpl {

    private static final FilterSpell filterElf = new FilterSpell("an Elf spell");

    static {
        filterElf.add(SubType.ELF.getPredicate());
    }

    public LysAlanaBowmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(ReachAbility.getInstance());
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(2)
                .setText("{this} deal 2 damage to target creature with flying"), filterElf, true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private LysAlanaBowmaster(final LysAlanaBowmaster card) {
        super(card);
    }

    @Override
    public LysAlanaBowmaster copy() {
        return new LysAlanaBowmaster(this);
    }
}
