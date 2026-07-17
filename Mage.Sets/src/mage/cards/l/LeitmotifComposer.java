package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeitmotifComposer extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell with mana value 5 or greater");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("creatures named Leitmotif Composer");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
        filter2.add(new NamePredicate("Leitmotif Composer"));
    }

    public LeitmotifComposer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Whenever you cast an instant or sorcery spell with mana value 5 or greater, create a token that's a copy of this creature.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenCopySourceEffect(), filter, false));

        // {2}{U}: Creatures named Leitmotif Composer can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(new CantBeBlockedAllEffect(filter2, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{U}")));
    }

    private LeitmotifComposer(final LeitmotifComposer card) {
        super(card);
    }

    @Override
    public LeitmotifComposer copy() {
        return new LeitmotifComposer(this);
    }
}
