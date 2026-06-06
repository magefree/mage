package mage.cards.h;

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
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE}, "{U}{U}",
                "Mesmeric Glare",
                new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Hypnotic Sprite
        this.getLeftHalfCard().setPT(2, 1);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Mesmeric Glare
        // Counter target spell with converted mana cost 3 or less.
        this.getRightHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetSpell(filter));

        finalizeCard();
    }

    private HypnoticSprite(final HypnoticSprite card) {
        super(card);
    }

    @Override
    public HypnoticSprite copy() {
        return new HypnoticSprite(this);
    }
}
