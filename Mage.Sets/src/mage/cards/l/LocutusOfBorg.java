package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BorgToken;

/**
 *
 * @author muz
 */
public final class LocutusOfBorg extends CardImpl {

    public LocutusOfBorg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BORG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Artifact creatures you control have "Whenever this creature deals combat damage to a player, create a 1/1 colorless Borg artifact creature token."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new BorgToken())),
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private LocutusOfBorg(final LocutusOfBorg card) {
        super(card);
    }

    @Override
    public LocutusOfBorg copy() {
        return new LocutusOfBorg(this);
    }
}
