
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.AuraSpellPredicate;

/**
 *
 * @author LoneFox
 */
public final class FugitiveDruid extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Aura spell");

    static {
        filter.add(AuraSpellPredicate.instance);
    }

    public FugitiveDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Fugitive Druid becomes the target of an Aura spell, you draw a card.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(new DrawCardSourceControllerEffect(1, "you"), filter));
    }

    private FugitiveDruid(final FugitiveDruid card) {
        super(card);
    }

    @Override
    public FugitiveDruid copy() {
        return new FugitiveDruid(this);
    }
}
