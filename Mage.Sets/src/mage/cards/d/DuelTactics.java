package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DuelTactics extends CardImpl {

    public DuelTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Duel Tactics deals 1 damage to target creature. It can't block this turn.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn).withTargetDescription("It"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {1}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{R}")));
    }

    private DuelTactics(final DuelTactics card) {
        super(card);
    }

    @Override
    public DuelTactics copy() {
        return new DuelTactics(this);
    }
}
