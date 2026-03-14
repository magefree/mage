package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class RimeChill extends CardImpl {

    public RimeChill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{6}{U}");

        // Vivid -- This spell costs {1} less to cast for each color among permanents you control.
        Ability vividAbility = new SimpleStaticAbility(
            Zone.ALL,
            new SpellCostReductionForEachSourceEffect(1, ColorsAmongControlledPermanentsCount.ALL_PERMANENTS)
        );
        vividAbility.setRuleAtTheTop(true);
        vividAbility.setAbilityWord(AbilityWord.VIVID);
        vividAbility.addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint());
        this.addAbility(vividAbility);

        // Tap up to two target creatures. Put a stun counter on each of them.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(
            new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText("put a stun counter on each of them")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private RimeChill(final RimeChill card) {
        super(card);
    }

    @Override
    public RimeChill copy() {
        return new RimeChill(this);
    }
}
