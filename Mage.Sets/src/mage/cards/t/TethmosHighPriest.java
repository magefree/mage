
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class TethmosHighPriest extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public TethmosHighPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Heroic — Whenever you cast a spell that targets Tethmos High Priest, return target creature card with converted mana cost 2 or less from your graveyard to the battlefield.
        Ability ability = new HeroicAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false);
        Target target = new TargetCardInYourGraveyard(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private TethmosHighPriest(final TethmosHighPriest card) {
        super(card);
    }

    @Override
    public TethmosHighPriest copy() {
        return new TethmosHighPriest(this);
    }
}
