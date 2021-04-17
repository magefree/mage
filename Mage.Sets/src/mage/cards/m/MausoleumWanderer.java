
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class MausoleumWanderer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another Spirit");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public MausoleumWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever another Spirit enters the battlefield under your control, Mausoleum Wanderer gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter));

        // Sacrifice Mausoleum Wanderer: Counter target instant or sorcery spell unless its controller pays {X}, where X is Mausoleum Wanderer's power.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new SourcePermanentPowerCount()), new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(new FilterInstantOrSorcerySpell()));
        this.addAbility(ability);
    }

    private MausoleumWanderer(final MausoleumWanderer card) {
        super(card);
    }

    @Override
    public MausoleumWanderer copy() {
        return new MausoleumWanderer(this);
    }
}
