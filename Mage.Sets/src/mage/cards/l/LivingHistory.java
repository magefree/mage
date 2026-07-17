package mage.cards.l;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CardLeftYourGraveyardThisTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Spirit22RedWhiteToken;
import mage.target.common.TargetAttackingCreature;
import mage.watchers.common.CardsLeftGraveyardWatcher;

/**
 *
 * @author muz
 */
public final class LivingHistory extends CardImpl {

    public LivingHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // When this enchantment enters, create a 2/2 red and white Spirit creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Spirit22RedWhiteToken())));

        // Whenever you attack, if a card left your graveyard this turn, target attacking creature gets +2/+0 until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostTargetEffect(2, 0), 1)
            .withInterveningIf(CardLeftYourGraveyardThisTurnCondition.instance);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability, new CardsLeftGraveyardWatcher());
    }

    private LivingHistory(final LivingHistory card) {
        super(card);
    }

    @Override
    public LivingHistory copy() {
        return new LivingHistory(this);
    }
}
