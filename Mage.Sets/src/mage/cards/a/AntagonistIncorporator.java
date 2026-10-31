package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.SubType;
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
public final class AntagonistIncorporator extends CardImpl {

    public AntagonistIncorporator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Artifact creatures you control have "Whenever this creature deals combat damage to a player, you may pay {1}. If you do, create a 1/1 colorless Borg artifact creature token."
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new BorgToken()), new GenericManaCost(1)
            )),
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private AntagonistIncorporator(final AntagonistIncorporator card) {
        super(card);
    }

    @Override
    public AntagonistIncorporator copy() {
        return new AntagonistIncorporator(this);
    }
}
