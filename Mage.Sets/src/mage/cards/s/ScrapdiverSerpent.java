

package mage.cards.s;

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

import java.util.UUID;

/**
 * @author ayratn
 */
public final class ScrapdiverSerpent extends CardImpl {

    public ScrapdiverSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Scrapdiver Serpent can't be blocked as long as defending player controls an artifact
        Effect effect = new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                new DefendingPlayerControlsSourceAttackingCondition(new FilterArtifactPermanent()));
        effect.setText("{this} can't be blocked as long as defending player controls an artifact");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private ScrapdiverSerpent(final ScrapdiverSerpent card) {
        super(card);
    }

    @Override
    public ScrapdiverSerpent copy() {
        return new ScrapdiverSerpent(this);
    }

}
