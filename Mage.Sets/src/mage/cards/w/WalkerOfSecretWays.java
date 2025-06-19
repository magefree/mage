package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WalkerOfSecretWays extends CardImpl {

    private static final FilterPermanent filterCreature = new FilterControlledPermanent(SubType.NINJA, "Ninja you control");

    public WalkerOfSecretWays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Ninjutsu {1}{U} ({1}{U}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{1}{U}"));

        // Whenever Walker of Secret Ways deals combat damage to a player, look at that player's hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new LookAtTargetPlayerHandEffect(), false, true
        ));

        // {1}{U}: Return target Ninja you control to its owner's hand. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{1}{U}"), MyTurnCondition.instance
        );
        ability.addTarget(new TargetPermanent(filterCreature));
        this.addAbility(ability);
    }

    private WalkerOfSecretWays(final WalkerOfSecretWays card) {
        super(card);
    }

    @Override
    public WalkerOfSecretWays copy() {
        return new WalkerOfSecretWays(this);
    }
}
