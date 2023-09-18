
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DeepwaterHypnotist extends CardImpl {
            
    public DeepwaterHypnotist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Inspired</i> &mdash; Whenever Deepwater Hypnotist becomes untapped, target creature an opponent controls gets -3/-0 until end of turn.
        Ability ability = new InspiredAbility(new BoostTargetEffect(-3,0,Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);        
    }

    private DeepwaterHypnotist(final DeepwaterHypnotist card) {
        super(card);
    }

    @Override
    public DeepwaterHypnotist copy() {
        return new DeepwaterHypnotist(this);
    }
}
