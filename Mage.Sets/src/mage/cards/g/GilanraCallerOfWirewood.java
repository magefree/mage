package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GilanraCallerOfWirewood extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 6 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }

    public GilanraCallerOfWirewood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Add {G}. When you spend this mana to cast a spell with converted mana cost 6 or greater, draw a card.
        BasicManaAbility ability = new GreenManaAbility();
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new ManaSpentDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), filter)
        ));
        ability.setUndoPossible(false);
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private GilanraCallerOfWirewood(final GilanraCallerOfWirewood card) {
        super(card);
    }

    @Override
    public GilanraCallerOfWirewood copy() {
        return new GilanraCallerOfWirewood(this);
    }
}
