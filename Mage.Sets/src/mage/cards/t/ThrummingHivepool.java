package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AffinityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.SliverToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThrummingHivepool extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SLIVER, "Slivers");

    public ThrummingHivepool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // Affinity for Slivers
        this.addAbility(new AffinityAbility(AffinityType.SLIVERS));

        // Slivers you control have double strike and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and haste"));
        this.addAbility(ability);

        // At the beginning of your upkeep, create two 1/1 colorless Sliver creature tokens.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new SliverToken(), 2)));
    }

    private ThrummingHivepool(final ThrummingHivepool card) {
        super(card);
    }

    @Override
    public ThrummingHivepool copy() {
        return new ThrummingHivepool(this);
    }
}
