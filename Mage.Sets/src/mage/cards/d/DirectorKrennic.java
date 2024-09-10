package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.TrooperToken2;
import mage.target.TargetPermanent;

/**
 *
 * @author NinthWorld
 */
public final class DirectorKrennic extends CardImpl {

    private static final FilterPermanent filterLand = new FilterPermanent("basic land");
    
    static {
        filterLand.add(CardType.LAND.getPredicate());
        filterLand.add(SuperType.BASIC.getPredicate());
    }
    
    public DirectorKrennic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Director Krennic enters the battlefield, create two 1/1 black Trooper creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TrooperToken2(), 2)));
        
        // When Director Krennic leaves the battlefield, destroy target basic land.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filterLand));
        this.addAbility(ability);
    }

    private DirectorKrennic(final DirectorKrennic card) {
        super(card);
    }

    @Override
    public DirectorKrennic copy() {
        return new DirectorKrennic(this);
    }
}
