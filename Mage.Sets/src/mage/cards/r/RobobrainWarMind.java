package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author justinjohnson14
 */
public final class RobobrainWarMind extends CardImpl {
    private static FilterPermanent filter = new FilterArtifactCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RobobrainWarMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Robobrain War Mind's power is equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(CardsInControllerHandCount.instance)));

        // When Robobrain War Mind enters the battlefield, you get an amount of {E} equal to the number of artifact creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(new PermanentsOnBattlefieldCount(filter))));

        // Whenever Robobrain War Mind attacks, you may pay {E}{E}{E}. If you do, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new PayEnergyCost(3))));
    }

    private RobobrainWarMind(final RobobrainWarMind card) {
        super(card);
    }

    @Override
    public RobobrainWarMind copy() {
        return new RobobrainWarMind(this);
    }
}
