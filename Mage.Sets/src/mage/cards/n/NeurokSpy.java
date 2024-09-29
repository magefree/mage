
package mage.cards.n;

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
public final class NeurokSpy extends CardImpl {

    public NeurokSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //Neurok Spy can't be blocked as long as defending player controls an artifact.
        Effect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                new DefendingPlayerControlsSourceAttackingCondition(new FilterArtifactPermanent()));
        effect.setText("{this} can't be blocked as long as defending player controls an artifact");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private NeurokSpy(final NeurokSpy card) {
        super(card);
    }

    @Override
    public NeurokSpy copy() {
        return new NeurokSpy(this);
    }
}
