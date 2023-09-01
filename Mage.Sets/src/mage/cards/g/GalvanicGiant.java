package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Xanderhall
 */
public final class GalvanicGiant extends AdventureCard {

    private static FilterSpell filter = new FilterSpell("a spell with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 5));
    }

    public GalvanicGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{U}", "Storm Reading", "{5}{U}{U}");
        
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a spell with mana value 5 or greater, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), filter, false);
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(1)).setText("and put a stun counter on it."));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Storm Reading
        // Draw four cards, then discard two cards.
        this.getSpellCard().getSpellAbility().addEffect(new DrawDiscardControllerEffect(4, 2));

        this.finalizeAdventure();
    }

    private GalvanicGiant(final GalvanicGiant card) {
        super(card);
    }

    @Override
    public GalvanicGiant copy() {
        return new GalvanicGiant(this);
    }
}
