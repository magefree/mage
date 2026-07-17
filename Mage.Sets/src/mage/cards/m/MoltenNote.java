package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoltenNote extends CardImpl {

    public MoltenNote(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{W}");

        // Molten Note deals damage to target creature equal to the amount of mana spent to cast this spell. Untap all creatures you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManaSpentToCastCount.instance)
                .setText("{this} deals damage to target creature equal to the amount of mana spent to cast this spell"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES));

        // Flashback {6}{R}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{R}{W}")));
    }

    private MoltenNote(final MoltenNote card) {
        super(card);
    }

    @Override
    public MoltenNote copy() {
        return new MoltenNote(this);
    }
}
