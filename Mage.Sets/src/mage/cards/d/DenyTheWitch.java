package mage.cards.d;

import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DenyTheWitch extends CardImpl {

    public DenyTheWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{U}{B}");

        // Counter target spell, activated ability, or triggered ability. Its controller loses life equal to the number of creatures you control.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetStackObject());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(CreaturesYouControlCount.instance));
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private DenyTheWitch(final DenyTheWitch card) {
        super(card);
    }

    @Override
    public DenyTheWitch copy() {
        return new DenyTheWitch(this);
    }
}
