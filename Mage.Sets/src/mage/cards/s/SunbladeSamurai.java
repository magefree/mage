package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunbladeSamurai extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Plains card");

    static {
        filter.add(SubType.PLAINS.getPredicate());
        filter.add(SuperType.BASIC.getPredicate());
    }

    public SunbladeSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Channel — {2}, Discard Sunblade Samurai: Search your library for a basic Plains card, reveal it, put it into your hand, the shuffle. You gain 2 life.
        Ability ability = new ChannelAbility(
                "{2}",
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true, true
                )
        );
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);
    }

    private SunbladeSamurai(final SunbladeSamurai card) {
        super(card);
    }

    @Override
    public SunbladeSamurai copy() {
        return new SunbladeSamurai(this);
    }
}
