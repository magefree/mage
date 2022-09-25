package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TorrentOfStone extends CardImpl {
    private static final FilterControlledLandPermanent filterSacrifice = new FilterControlledLandPermanent("two Mountains");

    static {
        filterSacrifice.add(SubType.MOUNTAIN.getPredicate());
    }

    public TorrentOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");
        this.subtype.add(SubType.ARCANE);

        // Torrent of Stone deals 4 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("4 damage"));
        // Splice onto Arcane-Sacrifice two Mountains.
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, new SacrificeTargetCost(new TargetControlledPermanent(2,2, filterSacrifice, false))));
    }

    private TorrentOfStone(final TorrentOfStone card) {
        super(card);
    }

    @Override
    public TorrentOfStone copy() {
        return new TorrentOfStone(this);
    }
}
