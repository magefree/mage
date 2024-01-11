package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class DugganPrivateDetective extends CardImpl {

    public DugganPrivateDetective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Duggan's power and toughness are each equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CardsInControllerHandCount.instance)));

        // Whenever Duggan enters the battlefield or attacks, investigate.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new InvestigateEffect().setText("investigate")));

        // The Most Important Punch in History -- {1}{G}, {T}: Duggan deals damage equal to twice its power to another target creature. Activate only once.
        Ability ability = new ActivateOncePerGameActivatedAbility(
                Zone.BATTLEFIELD,
                new DamageTargetEffect(new MultipliedValue(new SourcePermanentPowerCount(), 2))
                        .setText("{this} deals damage equal to twice its power to another target creature"),
                new ManaCostsImpl<>("{1}{G}"),
                TimingRule.INSTANT);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        ability.withFlavorWord("The Most Important Punch in History");
        this.addAbility(ability);
    }

    private DugganPrivateDetective(final DugganPrivateDetective card) {
        super(card);
    }

    @Override
    public DugganPrivateDetective copy() {
        return new DugganPrivateDetective(this);
    }
}
