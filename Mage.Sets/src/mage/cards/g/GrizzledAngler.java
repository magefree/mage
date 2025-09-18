package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GrizzledAngler extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(ColorlessPredicate.instance);
    }

    private static final Condition condition = new CardsInControllerGraveyardCondition(1, filter);

    public GrizzledAngler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.g.GrislyAnglerfish.class;

        // {T}: Put the top two cards of your library into your graveyard. Then if there is a colorless creature card in your graveyard, transform Grizzled Angler.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(new MillCardsControllerEffect(2), new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(), condition,
                "Then if there is a colorless creature card in your graveyard, transform {this}"
        ));
        this.addAbility(ability);
    }

    private GrizzledAngler(final GrizzledAngler card) {
        super(card);
    }

    @Override
    public GrizzledAngler copy() {
        return new GrizzledAngler(this);
    }
}
