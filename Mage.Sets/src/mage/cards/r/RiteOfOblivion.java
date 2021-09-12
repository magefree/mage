package mage.cards.r;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class RiteOfOblivion extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland permanent");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public RiteOfOblivion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{B}");
        

        // As an additional cost to cast this spell, sacrifice a nonland permanent.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));

        // Exile target nonland permanent
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Flashback {2}{W}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{W}{B}")));

    }

    private RiteOfOblivion(final RiteOfOblivion card) {
        super(card);
    }

    @Override
    public RiteOfOblivion copy() {
        return new RiteOfOblivion(this);
    }
}
