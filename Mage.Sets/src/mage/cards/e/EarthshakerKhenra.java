
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EarthshakerKhenra extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power less than or equal to this creature's power");

    static {
        filter.add(EarthshakerKhenraPredicate.instance);
    }

    public EarthshakerKhenra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Earthshaker Khenra enters the battlefield, target creature with power less than or equal to Earthshaker Khenra's power can't block this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn)
                        .setText("target creature with power less than or equal " +
                                "to {this}'s power can't block this turn")
        );
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Eternalize {4}{R}{R}
        this.addAbility(new EternalizeAbility(new ManaCostsImpl("{4}{R}{R}"), this));
    }

    private EarthshakerKhenra(final EarthshakerKhenra card) {
        super(card);
    }

    @Override
    public EarthshakerKhenra copy() {
        return new EarthshakerKhenra(this);
    }
}

enum EarthshakerKhenraPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(input.getSourceId());
        return sourcePermanent != null && input.getObject().getPower().getValue() <= sourcePermanent.getPower().getValue();
    }
}
