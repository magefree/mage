package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BlessedAlliance extends CardImpl {

    private static final FilterPlayer filterSacrifice = new FilterPlayer("opponent to sacrifice an attacking creature");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creatures to untap");
    private static final FilterPlayer filterGainLife = new FilterPlayer("player to gain life");

    static {
        filterSacrifice.add(TargetController.OPPONENT.getPlayerPredicate());
    }

    public BlessedAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Escalate {2}
        this.addAbility(new EscalateAbility(new GenericManaCost(2)));

        // Choose one or more —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // Target player gains 4 life.
        Effect effect = new GainLifeTargetEffect(4);
        effect.setText("Target player gains 4 life");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer(1, 1, false, filterGainLife).withChooseHint("player gains 4 life"));

        // Untap up to two target creatures.
        effect = new UntapTargetEffect();
        effect.setText("Untap up to two target creatures");
        Mode mode = new Mode(effect);
        mode.addTarget(new TargetCreaturePermanent(0, 2, filterCreature, false).withChooseHint("untap"));
        this.getSpellAbility().addMode(mode);

        // Target opponent sacrifices an attacking creature.
        mode = new Mode(new SacrificeEffect(new FilterAttackingCreature(), 1, "Target opponent"));
        mode.addTarget(new TargetPlayer(1, 1, false, filterSacrifice).withChooseHint("sacrifices an attacking creature"));
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
