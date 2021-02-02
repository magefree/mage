
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class DrossScorpion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creature");
    
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }
    
    public DrossScorpion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.SCORPION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Dross Scorpion or another artifact creature dies, you may untap target artifact.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new UntapTargetEffect(), true, filter);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private DrossScorpion(final DrossScorpion card) {
        super(card);
    }

    @Override
    public DrossScorpion copy() {
        return new DrossScorpion(this);
    }
}
