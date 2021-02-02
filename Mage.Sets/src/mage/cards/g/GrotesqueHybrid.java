
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class GrotesqueHybrid extends CardImpl {

    public GrotesqueHybrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Grotesque Hybrid deals combat damage to a creature, destroy that creature. It can't be regenerated.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new DestroyTargetEffect(true), false, true));

        // Discard a card: Grotesque Hybrid gains flying and protection from green and from white until end of turn.
        Effect effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("{this} gains flying");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new DiscardCardCost());
        effect = new GainAbilitySourceEffect(ProtectionAbility.from(ObjectColor.GREEN, ObjectColor.WHITE), Duration.EndOfTurn);
        effect.setText("and protection from green and from white until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GrotesqueHybrid(final GrotesqueHybrid card) {
        super(card);
    }

    @Override
    public GrotesqueHybrid copy() {
        return new GrotesqueHybrid(this);
    }
}
