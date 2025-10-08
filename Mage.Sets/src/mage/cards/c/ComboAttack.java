package mage.cards.c;

import mage.abilities.effects.common.TargetsDamageTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterTeamCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ComboAttack extends CardImpl {

    private static final FilterPermanent filter = new FilterTeamCreaturePermanent("creatures your team controls");

    public ComboAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Two target creatures your team controls each deal damage equal to their power to target creature.
        this.getSpellAbility().addEffect(new TargetsDamageTargetsEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(2, filter).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(3));
    }

    private ComboAttack(final ComboAttack card) {
        super(card);
    }

    @Override
    public ComboAttack copy() {
        return new ComboAttack(this);
    }
}
