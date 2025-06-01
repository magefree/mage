package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlibouAncientWitness extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("tapped artifacts you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Tapped artifacts you control", xValue);

    public AlibouAncientWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Other artifact creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE, true
        )));

        // Whenever one or more artifact creatures you control attack, Alibou, Ancient Witness deals X damage to any target and you scry X, where X is the number of tapped artifacts you control.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new DamageTargetEffect(xValue).setText("{this} deals X damage to any target"),
                1, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setTriggerPhrase("Whenever one or more artifact creatures you control attack, ");
        ability.addEffect(new ScryEffect(xValue).concatBy("and you"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.addHint(hint));
    }

    private AlibouAncientWitness(final AlibouAncientWitness card) {
        super(card);
    }

    @Override
    public AlibouAncientWitness copy() {
        return new AlibouAncientWitness(this);
    }
}