package mage.cards.a;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbandonThePost extends CardImpl {

    public AbandonThePost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Up to two target creatures can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}")));
    }

    private AbandonThePost(final AbandonThePost card) {
        super(card);
    }

    @Override
    public AbandonThePost copy() {
        return new AbandonThePost(this);
    }
}
