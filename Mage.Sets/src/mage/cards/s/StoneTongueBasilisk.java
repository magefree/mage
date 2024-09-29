package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class StoneTongueBasilisk extends CardImpl {

    public StoneTongueBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");
        this.subtype.add(SubType.BASILISK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Stone-Tongue Basilisk deals combat damage to a creature, destroy that creature at end of combat.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true
        ).setText("destroy that creature at end of combat"), false, true));

        // Threshold - As long as seven or more cards are in your graveyard, all creatures able to block Stone-Tongue Basilisk do so.
        this.addAbility(new SimpleStaticAbility(new ConditionalRequirementEffect(
                new MustBeBlockedByAllSourceEffect(), ThresholdCondition.instance, "As long as seven " +
                "or more cards are in your graveyard,  all creatures able to block {this} do so"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private StoneTongueBasilisk(final StoneTongueBasilisk card) {
        super(card);
    }

    @Override
    public StoneTongueBasilisk copy() {
        return new StoneTongueBasilisk(this);
    }
}
