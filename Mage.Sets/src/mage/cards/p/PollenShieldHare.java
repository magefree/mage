
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PollenShieldHare extends AdventureCard {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES);

    private static final Hint hint = new ValueHint("Number of controlled creatures", xValue);

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creature tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PollenShieldHare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{W}", "Hare Raising", "{G}");
        this.subtype.add(SubType.RABBIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creature tokens you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)
        ));

        // Hare Raising
        // Target creature you control gains vigilance and gets +X/+X until end of turn, where X is the number of creatures you control.
        this.getSpellCard().getSpellAbility().addEffect(
                new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                        .setText("Target creature you control gains vigilance")
        );
        this.getSpellCard().getSpellAbility().addEffect(
                new BoostTargetEffect(xValue, xValue)
                        .setText(" and gets +X/+X until end of turn, where X is the number of creatures you control.")
        );
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellCard().getSpellAbility().addHint(hint);
    }

    private PollenShieldHare(final PollenShieldHare card) {
        super(card);
    }

    @Override
    public PollenShieldHare copy() {
        return new PollenShieldHare(this);
    }
}