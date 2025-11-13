package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiritWorldToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaboonSpirit extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.SPIRIT, "another nontoken Spirit you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public BaboonSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MONKEY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever another nontoken Spirit you control enters, create a 1/1 colorless Spirit creature token with "This token can't block or be blocked by non-Spirit creatures."
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new CreateTokenEffect(new SpiritWorldToken()), filter));

        // {3}{U}: Exile another target creature you control. Return it to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(
                new ExileReturnBattlefieldNextEndStepTargetEffect(), new ManaCostsImpl<>("{3}{U}")
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private BaboonSpirit(final BaboonSpirit card) {
        super(card);
    }

    @Override
    public BaboonSpirit copy() {
        return new BaboonSpirit(this);
    }
}
