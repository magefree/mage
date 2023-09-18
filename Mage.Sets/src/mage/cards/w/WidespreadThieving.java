package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WidespreadThieving extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a multicolored spell");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public WidespreadThieving(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Hideaway 5
        this.addAbility(new HideawayAbility(5));

        // Whenever you cast a multicolored spell, create a Treasure token. Then you may pay {W}{U}{B}{R}{G}. If you do, you may play the exiled card without paying its mana cost.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), filter, false
        );
        ability.addEffect(new DoIfCostPaid(
                new HideawayPlayEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        ).concatBy("Then"));
        this.addAbility(ability);
    }

    private WidespreadThieving(final WidespreadThieving card) {
        super(card);
    }

    @Override
    public WidespreadThieving copy() {
        return new WidespreadThieving(this);
    }
}
