package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShieldWallSentinel extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a creature card with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public ShieldWallSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Shield-Wall Sentinel enters the battlefield, you may search your library for a creature card with defender, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true
        ), true));
    }

    private ShieldWallSentinel(final ShieldWallSentinel card) {
        super(card);
    }

    @Override
    public ShieldWallSentinel copy() {
        return new ShieldWallSentinel(this);
    }
}
