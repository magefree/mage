package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Rockalanche extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.FOREST, "Forests you control"), null
    );
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    public Rockalanche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        this.subtype.add(SubType.LESSON);

        // Earthbend X, where X is the number of Forests you control.
        this.getSpellAbility().addEffect(new EarthbendTargetEffect(xValue));
        this.getSpellAbility().addTarget(new TargetControlledLandPermanent());
        this.getSpellAbility().addHint(hint);

        // Flashback {5}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{G}")));
    }

    private Rockalanche(final Rockalanche card) {
        super(card);
    }

    @Override
    public Rockalanche copy() {
        return new Rockalanche(this);
    }
}
