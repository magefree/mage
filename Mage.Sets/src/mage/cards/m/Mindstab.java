
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class Mindstab extends CardImpl {

    public Mindstab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}");


        // Target player discards three cards.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Suspend 4—{B} (Rather than cast this card from your hand, you may pay {B} and exile it with four time counters on it. At the beginning of your upkeep, remove a time counter. When the last is removed, cast it without paying its mana cost.)
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{B}"), this));
    }

    private Mindstab(final Mindstab card) {
        super(card);
    }

    @Override
    public Mindstab copy() {
        return new Mindstab(this);
    }
}
