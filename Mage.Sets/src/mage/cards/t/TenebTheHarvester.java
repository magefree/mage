
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class TenebTheHarvester extends CardImpl {

    public TenebTheHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Teneb, the Harvester deals combat damage to a player, you may pay {2}{B}. If you do, put target creature card from a graveyard onto the battlefield under your control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoIfCostPaid(new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{2}{B}")), false);
        Target target = new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard"));
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private TenebTheHarvester(final TenebTheHarvester card) {
        super(card);
    }

    @Override
    public TenebTheHarvester copy() {
        return new TenebTheHarvester(this);
    }
}
