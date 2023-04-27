package mage.cards.a;

import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlliedAssault extends CardImpl {

    public AlliedAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Up to two target creatures each get +X/+X until end of turn, where X is the number of creatures in your party.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                PartyCount.instance, PartyCount.instance, Duration.EndOfTurn
        ).setText("up to two target creatures each get +X/+X until end of turn, " +
                "where X is the number of creatures in your party. " + PartyCount.getReminder()
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addHint(PartyCountHint.instance);
    }

    private AlliedAssault(final AlliedAssault card) {
        super(card);
    }

    @Override
    public AlliedAssault copy() {
        return new AlliedAssault(this);
    }
}
