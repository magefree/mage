package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodHypnotist extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.BLOOD, "one or more Blood tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public BloodHypnotist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Blood Hypnotist can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever you sacrifice one or more Blood tokens, target creature can't block this turn. This ability triggers only once each turn.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), filter
        ).setTriggersOnceEachTurn(true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BloodHypnotist(final BloodHypnotist card) {
        super(card);
    }

    @Override
    public BloodHypnotist copy() {
        return new BloodHypnotist(this);
    }
}
