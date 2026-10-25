package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.TribbleToken;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KolothTribbleHunter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.TRIBBLE, "Tribbles");

    public KolothTribbleHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, each player creates two 1/1 white Tribble creature tokens with "This token can't block."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            new CreateTokenAllEffect(new TribbleToken(), 2, TargetController.ANY)
        ));

        // All Tribbles get +1/+0 and are goaded.
        Ability ability = new SimpleStaticAbility(new BoostAllEffect(1, 0, Duration.WhileOnBattlefield, filter, false));
        ability.addEffect(new GoadAllEffect(Duration.WhileOnBattlefield, filter, false).setText("and are goaded"));
        this.addAbility(ability);
    }

    private KolothTribbleHunter(final KolothTribbleHunter card) {
        super(card);
    }

    @Override
    public KolothTribbleHunter copy() {
        return new KolothTribbleHunter(this);
    }
}
