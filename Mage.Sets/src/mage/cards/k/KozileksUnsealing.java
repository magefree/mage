package mage.cards.k;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KozileksUnsealing extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a creature spell with mana value 4, 5, or 6");
    private static final FilterSpell filter2 = new FilterCreatureSpell("a creature spell with mana value 7 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 7));
        filter2.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 6));
    }

    public KozileksUnsealing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Whenever you cast a creature spell with mana value 4, 5, or 6, create two 0/1 colorless Eldrazi Spawn creature tokens with "Sacrifice this creature: Add {C}."
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new EldraziSpawnToken(), 2), filter, false
        ));

        // Whenever you cast a creature spell with mana value 7 or greater, draw three cards.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(3), filter2, false
        ));
    }

    private KozileksUnsealing(final KozileksUnsealing card) {
        super(card);
    }

    @Override
    public KozileksUnsealing copy() {
        return new KozileksUnsealing(this);
    }
}
