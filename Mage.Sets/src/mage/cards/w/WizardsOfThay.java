package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WizardsOfThay extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("Instant and sorcery spells");
    private static final FilterCard filter2 = new FilterCard("sorcery spells");

    static {
        filter2.add(CardType.SORCERY.getPredicate());
    }

    public WizardsOfThay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Myriad
        this.addAbility(new MyriadAbility());

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // You may cast sorcery spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter2)
        ));
    }

    private WizardsOfThay(final WizardsOfThay card) {
        super(card);
    }

    @Override
    public WizardsOfThay copy() {
        return new WizardsOfThay(this);
    }
}
