package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamahlHeartOfKrosa extends CardImpl {

    public KamahlHeartOfKrosa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of combat on your turn, creatures you control get +3/+3 and gain trample until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostControlledEffect(
                3, 3, Duration.EndOfTurn
        ).setText("creatures you control get +3/+3"), TargetController.YOU, false);
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("and gain trample until end of turn"));
        this.addAbility(ability);

        // {1}{G}: Until end of turn, target land you control becomes a 1/1 Elemental creature with vigilance, indestructible, and haste. It's still a land.
        ability = new SimpleActivatedAbility(new BecomesCreatureTargetEffect(new CreatureToken(
                1, 1, "1/1 Elemental creature with vigilance, indestructible, and haste"
        ).withSubType(SubType.ELEMENTAL)
                .withAbility(VigilanceAbility.getInstance())
                .withAbility(IndestructibleAbility.getInstance())
                .withAbility(HasteAbility.getInstance()),
                false, true, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private KamahlHeartOfKrosa(final KamahlHeartOfKrosa card) {
        super(card);
    }

    @Override
    public KamahlHeartOfKrosa copy() {
        return new KamahlHeartOfKrosa(this);
    }
}
