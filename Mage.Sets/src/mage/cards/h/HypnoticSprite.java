package mage.cards.h;

import mage.MageInt;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HypnoticSprite extends AdventureCard {

    private static final FilterSpell filter = new FilterSpell("spell with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public HypnoticSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{U}{U}", "Mesmeric Glare", "{2}{U}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Mesmeric Glare
        // Counter target spell with converted mana cost 3 or less.
        this.getSpellCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetSpell(filter));

        this.finalizeAdventure();
    }

    private HypnoticSprite(final HypnoticSprite card) {
        super(card);
    }

    @Override
    public HypnoticSprite copy() {
        return new HypnoticSprite(this);
    }
}
