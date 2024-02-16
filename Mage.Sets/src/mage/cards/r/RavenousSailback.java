package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenousSailback extends CardImpl {

    public RavenousSailback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Ravenous Sailback enters the battlefield, choose one --
        // * Ravenous Sailback gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
        );

        // * Destroy target artifact or enchantment.
        ability.addMode(new Mode(new DestroyTargetEffect()).addTarget(
                new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT)
        ));
        this.addAbility(ability);
    }

    private RavenousSailback(final RavenousSailback card) {
        super(card);
    }

    @Override
    public RavenousSailback copy() {
        return new RavenousSailback(this);
    }
}
