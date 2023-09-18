
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class ArenaAthlete extends CardImpl {
    
    public ArenaAthlete(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Heroic</i>  Whenever you cast a spell that targets Arena Athlete, target creature an opponent controls can't block this turn.
        Ability ability = new HeroicAbility(new CantBlockTargetEffect(Duration.EndOfTurn));
        TargetCreaturePermanent target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private ArenaAthlete(final ArenaAthlete card) {
        super(card);
    }

    @Override
    public ArenaAthlete copy() {
        return new ArenaAthlete(this);
    }
}
