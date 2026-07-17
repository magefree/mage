package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class KillmongerRuthlessUsurper extends CardImpl {

    public KillmongerRuthlessUsurper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Killmonger attacks, he gets +1/+0 until end of turn for each artifact defending player controls.
        this.addAbility(new AttacksTriggeredAbility(
            new BoostSourceEffect(
                new PermanentsTargetOpponentControlsCount(StaticFilters.FILTER_PERMANENT_ARTIFACT),
                StaticValue.get(0),
                Duration.EndOfTurn
            ).setText("he gets +1/+0 until end of turn for each artifact defending player controls"),
            false, null, SetTargetPointer.PLAYER
        ));

        // Whenever Killmonger deals combat damage to a player, that player sacrifices an artifact of their choice and you create a Treasure token.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new SacrificeEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT, 1, "that player"),
            false, true
        );
        ability.addEffect(new CreateTokenEffect(new TreasureToken()).concatBy("and you"));
        this.addAbility(ability);
    }

    private KillmongerRuthlessUsurper(final KillmongerRuthlessUsurper card) {
        super(card);
    }

    @Override
    public KillmongerRuthlessUsurper copy() {
        return new KillmongerRuthlessUsurper(this);
    }
}
