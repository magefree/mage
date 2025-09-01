
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.abilities.effects.common.LoseGameTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PhageTheUntouchable extends CardImpl {

    private static final Condition condition = new InvertCondition(CastFromHandSourcePermanentCondition.instance, "you didn't cast it from your hand");

    public PhageTheUntouchable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Phage the Untouchable enters the battlefield, if you didn't cast it from your hand, you lose the game.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LoseGameSourceControllerEffect())
                .withInterveningIf(condition), new CastFromHandWatcher());

        // Whenever Phage deals combat damage to a creature, destroy that creature. It can't be regenerated.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new DestroyTargetEffect(true), false, true));

        // Whenever Phage deals combat damage to a player, that player loses the game.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new LoseGameTargetPlayerEffect(), false, true));
    }

    private PhageTheUntouchable(final PhageTheUntouchable card) {
        super(card);
    }

    @Override
    public PhageTheUntouchable copy() {
        return new PhageTheUntouchable(this);
    }
}
