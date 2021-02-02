
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PlagueSliver extends CardImpl {

    private static final FilterCreaturePermanent filterSliver = new FilterCreaturePermanent();

    static {
        filterSliver.add(SubType.SLIVER.getPredicate());
    }

    public PlagueSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // All Slivers have "At the beginning of your upkeep, this permanent deals 1 damage to you."
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(1), TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield,
                filterSliver, "All Slivers have \"At the beginning of your upkeep, this permanent deals 1 damage to you.\"")));
    }

    private PlagueSliver(final PlagueSliver card) {
        super(card);
    }

    @Override
    public PlagueSliver copy() {
        return new PlagueSliver(this);
    }
}
