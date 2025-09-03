package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CosmicSpiderMan extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SPIDER, "Spiders");

    public CosmicSpiderMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of combat on your turn, other Spiders you control gain flying, first strike, trample, lifelink, and haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn, filter, true
        ).setText("other Spiders you control gain flying"));
        ability.addEffect(new GainAbilityControlledEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn, filter, true
        ).setText(", first strike"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, filter, true
        ).setText(", trample"));
        ability.addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn, filter, true
        ).setText(", lifelink"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn, filter, true
        ).setText(", and haste until end of turn"));
        this.addAbility(ability);
    }

    private CosmicSpiderMan(final CosmicSpiderMan card) {
        super(card);
    }

    @Override
    public CosmicSpiderMan copy() {
        return new CosmicSpiderMan(this);
    }
}
