package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.HumanSoldierToken;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrynnChampionOfFreedom extends CardImpl {

    public TrynnChampionOfFreedom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Silvar, Devourer of the Free
        this.addAbility(new PartnerWithAbility("Silvar, Devourer of the Free"));

        // At the beginning of your end step, if you attacked this turn, create a 1/1 white Human Soldier creature token.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new HumanSoldierToken()),
                TargetController.YOU,
                RaidCondition.instance,
                false
        );
        ability.addHint(RaidHint.instance);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private TrynnChampionOfFreedom(final TrynnChampionOfFreedom card) {
        super(card);
    }

    @Override
    public TrynnChampionOfFreedom copy() {
        return new TrynnChampionOfFreedom(this);
    }
}
