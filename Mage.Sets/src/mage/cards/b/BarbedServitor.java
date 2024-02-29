package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SuspectSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarbedServitor extends CardImpl {

    public BarbedServitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When Barbed Servitor enters the battlefield, suspect it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SuspectSourceEffect()));

        // Whenever Barbed Servitor deals combat damage to a player, you draw a card and you lose 1 life.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1, "you"), false
        );
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Whenever Barbed Servitor is dealt damage, target opponent loses that much life.
        ability = new DealtDamageToSourceTriggeredAbility(new LoseLifeTargetEffect(SavedDamageValue.MUCH), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BarbedServitor(final BarbedServitor card) {
        super(card);
    }

    @Override
    public BarbedServitor copy() {
        return new BarbedServitor(this);
    }
}
