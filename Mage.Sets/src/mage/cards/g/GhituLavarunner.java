
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Will
 */
public final class GhituLavarunner extends CardImpl{
    public GhituLavarunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // As long as there are two or more instant and/or sorcery cards in your graveyard, Ghitu Lavarunner gets +1/+0 and has haste.
        Ability ability = new SimpleStaticAbility(
            Zone.BATTLEFIELD,
            new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                new CardsInControllerGraveyardCondition(2, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY),
                "As long as there are two or more instant and/or sorcery cards in your graveyard, {this} gets +1/+0"));
        ability.addEffect(
            new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HasteAbility.getInstance()),
                new CardsInControllerGraveyardCondition(2, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY),
                "and has haste."));
        this.addAbility(ability);
    }

    public GhituLavarunner(final GhituLavarunner card) {
        super(card);
    }

    @Override
    public GhituLavarunner copy() {
        return new GhituLavarunner(this);
    }
}
