package mage.cards.t;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerminalAgony extends CardImpl {

    public TerminalAgony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Madness {B}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{B}{R}")));
    }

    private TerminalAgony(final TerminalAgony card) {
        super(card);
    }

    @Override
    public TerminalAgony copy() {
        return new TerminalAgony(this);
    }
}
