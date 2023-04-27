package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodboilSorcerer extends CardImpl {

    public BloodboilSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Bloodboil Sorcerer enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // Crown of Madness — {1}{R}, Sacrifice an artifact or creature: Goad target creature.
        Ability ability = new SimpleActivatedAbility(new GoadTargetEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Crown of Madness"));
    }

    private BloodboilSorcerer(final BloodboilSorcerer card) {
        super(card);
    }

    @Override
    public BloodboilSorcerer copy() {
        return new BloodboilSorcerer(this);
    }
}
