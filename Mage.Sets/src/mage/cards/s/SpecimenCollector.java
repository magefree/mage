package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.CrabToken;
import mage.game.permanent.token.SquirrelToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpecimenCollector extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public SpecimenCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Specimen Collector enters the battlefield, create a 1/1 green Squirrel creature token and a 0/3 blue Crab creature token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SquirrelToken()));
        ability.addEffect(new CreateTokenEffect(new CrabToken()).setText("and a 0/3 blue Crab creature token"));
        this.addAbility(ability);

        // When Specimen Collector dies, create a token that's a copy of target token you control.
        ability = new DiesSourceTriggeredAbility(new CreateTokenCopyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SpecimenCollector(final SpecimenCollector card) {
        super(card);
    }

    @Override
    public SpecimenCollector copy() {
        return new SpecimenCollector(this);
    }
}
