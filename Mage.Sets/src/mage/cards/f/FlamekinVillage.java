
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FlamekinVillage extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Elemental card from your hand");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    public FlamekinVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Flamekin Village enters the battlefield, you may reveal an Elemental card from your hand. If you don't, Flamekin Village enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new RevealTargetFromHandCost(new TargetCardInHand(filter))), "you may reveal a Elemental card from your hand. If you don't, {this} enters the battlefield tapped"));

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());
        
        // {R}, {tap}: Target creature gains haste until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private FlamekinVillage(final FlamekinVillage card) {
        super(card);
    }

    @Override
    public FlamekinVillage copy() {
        return new FlamekinVillage(this);
    }
}
