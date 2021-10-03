
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class MysticRetrieval extends CardImpl {
    private static final FilterCard filter = new FilterCard("instant or sorcery card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public MysticRetrieval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Return target instant or sorcery card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        // Flashback {2}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{2}{R}")));
    }

    private MysticRetrieval(final MysticRetrieval card) {
        super(card);
    }

    @Override
    public MysticRetrieval copy() {
        return new MysticRetrieval(this);
    }
}
