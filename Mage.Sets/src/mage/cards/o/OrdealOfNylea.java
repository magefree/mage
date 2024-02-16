
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.condition.common.AttachedToCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OrdealOfNylea extends CardImpl {

    public OrdealOfNylea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature attacks, put a +1/+1 counter on it. Then if it has three or more +1/+1 counters on it, sacrifice Ordeal of Nylea.
        ability = new AttacksAttachedTriggeredAbility(new AddCountersAttachedEffect(CounterType.P1P1.createInstance(),"it"), AttachmentType.AURA, false);
        ability.addEffect(new ConditionalOneShotEffect(new SacrificeSourceEffect(), new AttachedToCounterCondition(CounterType.P1P1, 3),
                "Then if it has three or more +1/+1 counters on it, sacrifice {this}"));
        this.addAbility(ability);
        // When you sacrifice Ordeal of Nylea, search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library.
        ability = new SacrificeSourceTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0,2, StaticFilters.FILTER_CARD_BASIC_LANDS),true),false);
        this.addAbility(ability);
    }

    private OrdealOfNylea(final OrdealOfNylea card) {
        super(card);
    }

    @Override
    public OrdealOfNylea copy() {
        return new OrdealOfNylea(this);
    }
}
