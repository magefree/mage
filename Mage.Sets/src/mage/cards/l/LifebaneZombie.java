
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class LifebaneZombie extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a green or white creature card");
    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.WHITE)));
    }

    public LifebaneZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
        // When Lifebane Zombie enters the battlefield, target opponent reveals their hand. You choose a green or white creature card from it and exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileCardYouChooseTargetOpponentEffect(filter));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);        
    }

    private LifebaneZombie(final LifebaneZombie card) {
        super(card);
    }

    @Override
    public LifebaneZombie copy() {
        return new LifebaneZombie(this);
    }
}
