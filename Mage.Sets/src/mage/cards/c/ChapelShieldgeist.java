package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChapelShieldgeist extends CardImpl {

    public ChapelShieldgeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Each creature you control has ward {1}.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("each creature you control has ward {1}")));

        // If Chapel Shieldgeist would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private ChapelShieldgeist(final ChapelShieldgeist card) {
        super(card);
    }

    @Override
    public ChapelShieldgeist copy() {
        return new ChapelShieldgeist(this);
    }
}
