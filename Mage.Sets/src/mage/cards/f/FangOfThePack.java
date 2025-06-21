package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MeleeAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class FangOfThePack extends CardImpl {

    public FangOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Melee
        this.addAbility(new MeleeAbility());

        // At the beginning of combat on your turn, another target creature you control gains melee until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(new MeleeAbility(), Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private FangOfThePack(final FangOfThePack card) {
        super(card);
    }

    @Override
    public FangOfThePack copy() {
        return new FangOfThePack(this);
    }
}
