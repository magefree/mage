package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArdozCobblerOfWar extends CardImpl {

    public ArdozCobblerOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Ardoz, Cobbler of War or another creature enters the battlefield under your control, that creature gets +2/+0 until end of turn.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostTargetEffect(2, 0)
                        .setText("that creature gets +2/+0 until end of turn"),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false,
                SetTargetPointer.PERMANENT, true
        ));

        // {3}{R}: Create a 1/1 red Goblin creature token with haste. Activate only as sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new GoblinToken(true)), new ManaCostsImpl<>("{3}{R}")
        ));
    }

    private ArdozCobblerOfWar(final ArdozCobblerOfWar card) {
        super(card);
    }

    @Override
    public ArdozCobblerOfWar copy() {
        return new ArdozCobblerOfWar(this);
    }
}
