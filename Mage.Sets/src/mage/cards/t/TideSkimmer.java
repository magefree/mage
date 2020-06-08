package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TideSkimmer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public TideSkimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you attack with two or more creatures with flying, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new DrawCardSourceControllerEffect(1), 2, filter
        ));
    }

    private TideSkimmer(final TideSkimmer card) {
        super(card);
    }

    @Override
    public TideSkimmer copy() {
        return new TideSkimmer(this);
    }
}
