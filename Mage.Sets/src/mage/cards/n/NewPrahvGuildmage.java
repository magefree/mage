
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class NewPrahvGuildmage extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent control");
 
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }
    
    public NewPrahvGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}{U}: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{W}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {3}{W}{U}: Detain target nonland permanent an opponent controls. 
        // (Until your next turn, that permanent can't attack or block and its activated abilities can't be activated.)
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DetainTargetEffect(), new ManaCostsImpl<>("{3}{W}{U}"));
        TargetNonlandPermanent target = new TargetNonlandPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private NewPrahvGuildmage(final NewPrahvGuildmage card) {
        super(card);
    }

    @Override
    public NewPrahvGuildmage copy() {
        return new NewPrahvGuildmage(this);
    }
}
