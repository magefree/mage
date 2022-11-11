package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectrumSentinel extends CardImpl {

    private static final FilterObject<?> filter = new FilterObject<>("multicolored");
    private static final FilterPermanent filter2 = new FilterLandPermanent();

    static {
        filter.add(MulticoloredPredicate.instance);
        filter2.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SpectrumSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Protection from multicolored
        this.addAbility(new ProtectionAbility(filter));

        // Whenever a nonbasic land enters the battlefield under an opponent's control, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new GainLifeEffect(1), filter2)
                .setTriggerPhrase("Whenever a nonbasic land enters the battlefield under an opponent's control, "));
    }

    private SpectrumSentinel(final SpectrumSentinel card) {
        super(card);
    }

    @Override
    public SpectrumSentinel copy() {
        return new SpectrumSentinel(this);
    }
}
