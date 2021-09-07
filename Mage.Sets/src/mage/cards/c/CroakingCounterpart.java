package mage.cards.c;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class CroakingCounterpart extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Frog creature");

    static {
        filter.add(Predicates.not(SubType.FROG.getPredicate()));
    }

    public CroakingCounterpart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{U}");

        // Create a token that's a copy of target non-Frog creature, except it's a 1/1 green Frog.
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, false, false,
                null, 1, 1, false
        );
        effect.setOnlyColor(ObjectColor.GREEN);
        effect.setOnlySubType(SubType.FROG);
        effect.setText("Create a token that's a copy of target non-Frog creature, except it's a 1/1 green Frog");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Flashback {3}{G}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{3}{G}{U}"), TimingRule.SORCERY));
    }

    private CroakingCounterpart(final CroakingCounterpart card) {
        super(card);
    }

    @Override
    public CroakingCounterpart copy() {
        return new CroakingCounterpart(this);
    }
}
