
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class BladewingTheRisen extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dragon creatures");
    private static final FilterPermanentCard filterCard = new FilterPermanentCard("Dragon permanent card from your graveyard");
    static {
        filter.add(SubType.DRAGON.getPredicate());
        filterCard.add(SubType.DRAGON.getPredicate());
    }

    public BladewingTheRisen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE, SubType.DRAGON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Bladewing the Risen enters the battlefield, you may return target Dragon permanent card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        Target target = new TargetCardInYourGraveyard(filterCard);
        ability.addTarget(target);
         this.addAbility(ability);
        // {B}{R}: Dragon creatures get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,1, Duration.EndOfTurn, filter, false), new ManaCostsImpl<>("{B}{R}")));
    }

    private BladewingTheRisen(final BladewingTheRisen card) {
        super(card);
    }

    @Override
    public BladewingTheRisen copy() {
        return new BladewingTheRisen(this);
    }
}
