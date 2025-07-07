package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SourceDealsNoncombatDamageToOpponentTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfBurningAlive extends CardImpl {

    public FearOfBurningAlive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Fear of Burning Alive enters, it deals 4 damage to each opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DamagePlayersEffect(4, TargetController.OPPONENT, "it")
        ));

        // Delirium -- Whenever a source you control deals noncombat damage to an opponent, if there are four or more card types among cards in your graveyard, Fear of Burning Alive deals that amount of damage to target creature that player controls.
        TriggeredAbility ability = new SourceDealsNoncombatDamageToOpponentTriggeredAbility(new DamageTargetEffect(SavedDamageValue.AMOUNT), SetTargetPointer.PLAYER);
        ability.addTarget(new TargetPermanent(new FilterCreaturePermanent("creature that player controls")));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        ability.withInterveningIf(DeliriumCondition.instance);
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        ability.addHint(CardTypesInGraveyardCount.YOU.getHint());
        this.addAbility(ability);
    }

    private FearOfBurningAlive(final FearOfBurningAlive card) {
        super(card);
    }

    @Override
    public FearOfBurningAlive copy() {
        return new FearOfBurningAlive(this);
    }
}
