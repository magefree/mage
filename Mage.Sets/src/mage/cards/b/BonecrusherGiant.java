package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BonecrusherGiant extends AdventureCard {

    public BonecrusherGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{R}", "Stomp", "{1}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Bonecrusher Giant becomes the target of a spell, Bonecrusher Giant deals 2 damage to that spell's controller.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(
                new DamageTargetEffect(
                        2, true, "that spell's controller", "{this}"
                ), StaticFilters.FILTER_SPELL_A, SetTargetPointer.PLAYER
        ).setTriggerPhrase("Whenever {this} becomes the target of a spell, "));

        // Stomp
        // Damage can’t be prevented this turn. Stomp deals 2 damage to any target.
        this.getSpellCard().getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn, "Damage can't be prevented this turn"));
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellCard().getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private BonecrusherGiant(final BonecrusherGiant card) {
        super(card);
    }

    @Override
    public BonecrusherGiant copy() {
        return new BonecrusherGiant(this);
    }
}
