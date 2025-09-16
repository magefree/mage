package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderManifestation extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public SpiderManifestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/G}");

        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());

        // Whenever you cast a spell with mana value 4 or greater, untap this creature.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false));
    }

    private SpiderManifestation(final SpiderManifestation card) {
        super(card);
    }

    @Override
    public SpiderManifestation copy() {
        return new SpiderManifestation(this);
    }
}
