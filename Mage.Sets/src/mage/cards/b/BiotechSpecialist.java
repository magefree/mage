package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.LanderToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiotechSpecialist extends CardImpl {

    public BiotechSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When this creature enters, create a Lander token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new LanderToken())));

        //  Whenever you sacrifice an artifact, this creature deals 2 damage to target opponent.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new DamageTargetEffect(2), StaticFilters.FILTER_PERMANENT_ARTIFACT
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BiotechSpecialist(final BiotechSpecialist card) {
        super(card);
    }

    @Override
    public BiotechSpecialist copy() {
        return new BiotechSpecialist(this);
    }
}
