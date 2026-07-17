package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConciliatorsDuelist extends CardImpl {

    public ConciliatorsDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{B}{B}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When this creature enters, draw a card. Each player loses 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new LoseLifeAllPlayersEffect(1));
        this.addAbility(ability);

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, exile up to one target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        ability = new ReparteeAbility(new ExileReturnBattlefieldNextEndStepTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private ConciliatorsDuelist(final ConciliatorsDuelist card) {
        super(card);
    }

    @Override
    public ConciliatorsDuelist copy() {
        return new ConciliatorsDuelist(this);
    }
}
