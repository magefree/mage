package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

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
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new DestroyTargetEffect()), true);
        effect.setText("destroy that creature at end of combat");
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(effect, false, true));

        // Threshold - As long as seven or more cards are in your graveyard, all creatures able to block Stone-Tongue Basilisk do so.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalRequirementEffect(
                new MustBeBlockedByAllSourceEffect(),
                new CardsInControllerGraveyardCondition(7),
                "As long as seven or more cards are in your graveyard,  all creatures able to block {this} do so"
        ));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private StoneTongueBasilisk(final StoneTongueBasilisk card) {
        super(card);
    }

    @Override
    public StoneTongueBasilisk copy() {
        return new StoneTongueBasilisk(this);
    }
}
