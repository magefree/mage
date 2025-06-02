package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.RebelRedToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarretAvalancheLeader extends CardImpl {

    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.REBEL);

    public BarretAvalancheLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Avalanche! -- Whenever an Equipment you control enters, create a 2/2 red Rebel creature token.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new RebelRedToken()), StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT
        ).withFlavorWord("Avalanche!"));

        // At the beginning of combat on your turn, attach up to one target Equipment you control to target Rebel you control.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AttachTargetToTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private BarretAvalancheLeader(final BarretAvalancheLeader card) {
        super(card);
    }

    @Override
    public BarretAvalancheLeader copy() {
        return new BarretAvalancheLeader(this);
    }
}
