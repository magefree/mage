package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class DampenThought extends CardImpl {

    public DampenThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");
        this.subtype.add(SubType.ARCANE);


        // Target player mills 4 cards.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer().withChooseHint("mills 4 cards"));
        // Splice onto Arcane {1}{U}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{1}{U}"));
    }

    private DampenThought(final DampenThought card) {
        super(card);
    }

    @Override
    public DampenThought copy() {
        return new DampenThought(this);
    }
}
