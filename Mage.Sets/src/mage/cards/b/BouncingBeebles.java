
package mage.cards.b;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DefendingPlayerControlsSourceAttackingCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;

/**
 * @author Backfir3
 */
public final class BouncingBeebles extends CardImpl {

    public BouncingBeebles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.BEEBLE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Bouncing Beebles can't be blocked as long as defending player controls an artifact.
        Effect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                new DefendingPlayerControlsSourceAttackingCondition(new FilterArtifactPermanent()));
        effect.setText("{this} can't be blocked as long as defending player controls an artifact");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BouncingBeebles(final BouncingBeebles card) {
        super(card);
    }

    @Override
    public BouncingBeebles copy() {
        return new BouncingBeebles(this);
    }
}
