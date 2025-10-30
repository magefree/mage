package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyggWanderwineWisdom extends CardImpl {

    public SyggWanderwineWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.s.SyggWanderbrineShield.class;

        // Sygg can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever this creature enters or transforms into Sygg, Wanderwine Wisdom, target creature gains "Whenever this creature deals combat damage to a player or planeswalker, draw a card" until end of turn.
        Ability ability = new TransformsOrEntersTriggeredAbility(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), false
                ), Duration.EndOfTurn
        ), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of your first main phase, you may pay {W}. If you do, transform Sygg.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{W}"))
        ));
    }

    private SyggWanderwineWisdom(final SyggWanderwineWisdom card) {
        super(card);
    }

    @Override
    public SyggWanderwineWisdom copy() {
        return new SyggWanderwineWisdom(this);
    }
}
