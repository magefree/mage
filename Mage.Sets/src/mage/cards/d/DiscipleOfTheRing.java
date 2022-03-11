
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class DiscipleOfTheRing extends CardImpl {

    public DiscipleOfTheRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {1}, Exile an instant or sorcery card from your graveyard:
        // Choose one - Counter target noncreature spell unless its controller pay {2};
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(2)), new GenericManaCost(1));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(1, new FilterInstantOrSorceryCard("an instant or sorcery card from your graveyard"))));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        
        // or Disciple of the Ring gets +1/+1 until end of turn; 
        Mode mode = new Mode(new BoostSourceEffect(1, 1, Duration.EndOfTurn));
        ability.addMode(mode);
        
        // or Tap target creature;
        mode = new Mode(new TapTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);
        
        // or Untap target creature.
        mode = new Mode(new UntapTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private DiscipleOfTheRing(final DiscipleOfTheRing card) {
        super(card);
    }

    @Override
    public DiscipleOfTheRing copy() {
        return new DiscipleOfTheRing(this);
    }
}
