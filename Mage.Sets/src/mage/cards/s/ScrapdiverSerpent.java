

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DefendingPlayerControlsCondition;
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
 *
 * @author ayratn
 */
public final class ScrapdiverSerpent extends CardImpl {

    public ScrapdiverSerpent (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Scrapdiver Serpent can't be blocked as long as defending player controls an artifact
        Effect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                new DefendingPlayerControlsCondition(new FilterArtifactPermanent()));
        effect.setText("{this} can't be blocked as long as defending player controls an artifact");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public ScrapdiverSerpent (final ScrapdiverSerpent card) {
        super(card);
    }

    @Override
    public ScrapdiverSerpent copy() {
        return new ScrapdiverSerpent(this);
    }

}
