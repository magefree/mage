package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BlessedAlliance extends CardImpl {

    public BlessedAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Escalate {2}
        this.addAbility(new EscalateAbility(new GenericManaCost(2)));

        // Choose one or more —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Target player gains 4 life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer().withChooseHint("player gains 4 life"));

        // Untap up to two target creatures.
        Mode mode = new Mode(new UntapTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(0, 2).withChooseHint("untap"));
        this.getSpellAbility().addMode(mode);

        // Target opponent sacrifices an attacking creature.
        mode = new Mode(new SacrificeEffect(new FilterAttackingCreature(), 1, "Target opponent"));
        mode.addTarget(new TargetPlayer().withChooseHint("sacrifices an attacking creature"));
        this.getSpellAbility().addMode(mode);
    }

    private BlessedAlliance(final BlessedAlliance card) {
        super(card);
    }

    @Override
    public BlessedAlliance copy() {
        return new BlessedAlliance(this);
    }
}
