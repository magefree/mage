package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaiAndZuko extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ally spells and artifact spells");

    static {
        filter.add(Predicates.or(
                SubType.ALLY.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public MaiAndZuko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Firebending 3
        this.addAbility(new FirebendingAbility(3));

        // You may cast Ally spells and artifact spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)));
    }

    private MaiAndZuko(final MaiAndZuko card) {
        super(card);
    }

    @Override
    public MaiAndZuko copy() {
        return new MaiAndZuko(this);
    }
}
