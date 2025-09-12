package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuinousRampage extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifacts with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public RuinousRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Choose one --
        // * Ruinous Rampage deals 3 damage to each opponent.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(3, TargetController.OPPONENT));

        // * Exile all artifacts with mana value 3 or less.
        this.getSpellAbility().addMode(new Mode(new ExileAllEffect(filter)));
    }

    private RuinousRampage(final RuinousRampage card) {
        super(card);
    }

    @Override
    public RuinousRampage copy() {
        return new RuinousRampage(this);
    }
}
