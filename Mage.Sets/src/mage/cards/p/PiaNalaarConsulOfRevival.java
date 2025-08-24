package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.PlayLandOrCastSpellTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiaNalaarConsulOfRevival extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.THOPTER, "Thopters");

    public PiaNalaarConsulOfRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Thopters you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever you play a land from exile or cast a spell from exile, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new PlayLandOrCastSpellTriggeredAbility(
                new CreateTokenEffect(new ThopterColorlessToken()), true, false
        ));
    }

    private PiaNalaarConsulOfRevival(final PiaNalaarConsulOfRevival card) {
        super(card);
    }

    @Override
    public PiaNalaarConsulOfRevival copy() {
        return new PiaNalaarConsulOfRevival(this);
    }
}
