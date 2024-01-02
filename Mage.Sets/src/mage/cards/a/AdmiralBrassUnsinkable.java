package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class AdmiralBrassUnsinkable extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("pirate creature card from your graveyard");

    static {
        filter.add(SubType.PIRATE.getPredicate());
    }

    public AdmiralBrassUnsinkable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Admiral Brass, Unsinkable enters the battlefield, mill four cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MillCardsControllerEffect(4), false)
        );

        // At the beginning of combat on your turn, you may return target Pirate creature card from your graveyard 
        // to the battlefield with a finality counter on it. It has base power and toughness 4/4. 
        // It gains haste until end of turn. (If a creature with a finality counter on it would die, exile it instead.)
        Effect returnFromGraveyardEffect = new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.FINALITY.createInstance());
        BeginningOfCombatTriggeredAbility ability = new BeginningOfCombatTriggeredAbility(
                returnFromGraveyardEffect,
                TargetController.YOU,
                true
        );
        ability.addEffect(new SetBasePowerToughnessTargetEffect(4, 4, Duration.WhileOnBattlefield).setText("It has base power and toughness 4/4."));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn. (If a creature with a finality counter on it would die, exile it instead.)"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

    }

    private AdmiralBrassUnsinkable(final AdmiralBrassUnsinkable card) {
        super(card);
    }

    @Override
    public AdmiralBrassUnsinkable copy() {
        return new AdmiralBrassUnsinkable(this);
    }
}
