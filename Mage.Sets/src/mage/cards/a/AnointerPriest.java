
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author LevelX2
 */
public final class AnointerPriest extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a creature token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public AnointerPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a creature token enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter));

        // Embalm {3}{W}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{3}{W}"), this));
    }

    private AnointerPriest(final AnointerPriest card) {
        super(card);
    }

    @Override
    public AnointerPriest copy() {
        return new AnointerPriest(this);
    }
}
