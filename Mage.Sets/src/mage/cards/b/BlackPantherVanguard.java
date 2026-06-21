package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SoldierToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class BlackPantherVanguard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.HERO, "another nontoken Hero");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public BlackPantherVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever another nontoken Hero you control enters, choose one --
        // * Create a 1/1 white Soldier creature token.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 1), filter);

        // * Creatures you control get +1/+1 until end of turn.
        Mode mode = new Mode(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private BlackPantherVanguard(final BlackPantherVanguard card) {
        super(card);
    }

    @Override
    public BlackPantherVanguard copy() {
        return new BlackPantherVanguard(this);
    }
}
