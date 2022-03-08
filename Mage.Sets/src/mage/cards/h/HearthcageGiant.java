
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.ElementalShamanToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class HearthcageGiant extends CardImpl {

    private static final FilterControlledPermanent filterElemental = new FilterControlledPermanent("Elemental");
    private static final FilterCreaturePermanent filterGiant = new FilterCreaturePermanent("Giant creature");

    static {
        filterElemental.add(SubType.ELEMENTAL.getPredicate());
        filterGiant.add(SubType.GIANT.getPredicate());
    }

    public HearthcageGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //When Hearthcage Giant enters the battlefield, create two 3/1 red Elemental Shaman creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ElementalShamanToken("LRW"), 2), false));

        //Sacrifice an Elemental: Target Giant creature gets +3/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(3, 1, Duration.EndOfTurn), new SacrificeTargetCost(new TargetControlledPermanent(filterElemental)));
        ability.addTarget(new TargetCreaturePermanent(filterGiant));
        this.addAbility(ability);
    }

    private HearthcageGiant(final HearthcageGiant card) {
        super(card);
    }

    @Override
    public HearthcageGiant copy() {
        return new HearthcageGiant(this);
    }
}
