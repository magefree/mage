package mage.cards.p;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PracticedTactics extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(PartyCount.instance, 2);

    public PracticedTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose target attacking or blocking creature. Practiced Tactics deals damage to that creature equal to twice the number of creatures in your party.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("choose target attacking or blocking creature. {this} deals damage to that creature " +
                        "equal to twice the number of creatures in your party. " + PartyCount.getReminder()));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addHint(PartyCountHint.instance);
    }

    private PracticedTactics(final PracticedTactics card) {
        super(card);
    }

    @Override
    public PracticedTactics copy() {
        return new PracticedTactics(this);
    }
}
