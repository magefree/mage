
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class ConclaveNaturalists extends CardImpl {

    public ConclaveNaturalists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Conclave Naturalists enters the battlefield, you may destroy target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private ConclaveNaturalists(final ConclaveNaturalists card) {
        super(card);
    }

    @Override
    public ConclaveNaturalists copy() {
        return new ConclaveNaturalists(this);
    }
}
