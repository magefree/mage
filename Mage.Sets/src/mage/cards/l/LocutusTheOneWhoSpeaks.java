package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class LocutusTheOneWhoSpeaks extends CardImpl {

    public LocutusTheOneWhoSpeaks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BORG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Artifact creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));

        // {W}{B}, Discard this card: It deals 2 damage to target creature. You gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.HAND, new DamageTargetEffect(2, "it"), new ManaCostsImpl<>("{W}{B}"));
        ability.addCost(new DiscardSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);
    }

    private LocutusTheOneWhoSpeaks(final LocutusTheOneWhoSpeaks card) {
        super(card);
    }

    @Override
    public LocutusTheOneWhoSpeaks copy() {
        return new LocutusTheOneWhoSpeaks(this);
    }
}
