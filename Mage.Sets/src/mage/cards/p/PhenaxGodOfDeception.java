
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class PhenaxGodOfDeception extends CardImpl {

    public PhenaxGodOfDeception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{3}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // As long as your devotion to blue and black is less than seven, Phenax isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.U, ColoredManaSymbol.B), 7);
        effect.setText("As long as your devotion to blue and black is less than seven, Phenax isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));           
        // Creatures you control have "{T}: Target player puts the top X cards of their library into their graveyard, where X is this creature's toughness."
        effect = new PutTopCardOfLibraryIntoGraveTargetEffect(SourcePermanentToughnessValue.getInstance());
        effect.setText("Target player puts the top X cards of their library into their graveyard, where X is this creature's toughness");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        effect = new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES,false);
        effect.setText("Creatures you control have \"{T}: Target player puts the top X cards of their library into their graveyard, where X is this creature's toughness.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public PhenaxGodOfDeception(final PhenaxGodOfDeception card) {
        super(card);
    }

    @Override
    public PhenaxGodOfDeception copy() {
        return new PhenaxGodOfDeception(this);
    }
}
