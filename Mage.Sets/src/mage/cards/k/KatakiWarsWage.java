
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

/**
 *
 * @author Loki
 */
public final class KatakiWarsWage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public KatakiWarsWage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        // All artifacts have "At the beginning of your upkeep, sacrifice this artifact unless you pay {1}."
        Ability gainedAbility = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new GenericManaCost(1)), TargetController.YOU, false);
        Effect effect = new GainAbilityAllEffect(gainedAbility, Duration.WhileOnBattlefield, filter, false);
        effect.setText("All artifacts have \"At the beginning of your upkeep, sacrifice this artifact unless you pay {1}.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private KatakiWarsWage(final KatakiWarsWage card) {
        super(card);
    }

    @Override
    public KatakiWarsWage copy() {
        return new KatakiWarsWage(this);
    }
}
