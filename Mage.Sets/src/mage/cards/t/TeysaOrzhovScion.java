
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class TeysaOrzhovScion extends CardImpl {
    
    private final static FilterControlledCreaturePermanent filterWhite = new FilterControlledCreaturePermanent("three white creatures");
    private final static FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("another black creature you control");
    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterBlack.add(new AnotherPredicate());
        filterBlack.add(new ControllerPredicate(TargetController.YOU));
    }

    public TeysaOrzhovScion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Sacrifice three white creatures: Exile target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(3, 3, filterWhite, true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // Whenever another black creature you control dies, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken("GPT")), false, filterBlack));
    }

    public TeysaOrzhovScion(final TeysaOrzhovScion card) {
        super(card);
    }

    @Override
    public TeysaOrzhovScion copy() {
        return new TeysaOrzhovScion(this);
    }
}
