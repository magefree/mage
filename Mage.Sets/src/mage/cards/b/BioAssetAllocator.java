package mage.cards.b;

import java.util.UUID;
import mage.constants.Duration;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BioAssetAllocator extends CardImpl {

    public BioAssetAllocator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Artifact creatures you control have "Whenever this creature attacks, each opponent loses 1 life and you gain 1 life."
        Ability gainedAbility = new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(1));
        gainedAbility.addEffect(new GainLifeEffect(1).concatBy("and"));

        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            gainedAbility, Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private BioAssetAllocator(final BioAssetAllocator card) {
        super(card);
    }

    @Override
    public BioAssetAllocator copy() {
        return new BioAssetAllocator(this);
    }
}
